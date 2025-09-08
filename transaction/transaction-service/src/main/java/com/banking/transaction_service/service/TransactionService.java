package com.banking.transaction_service.service;

import com.banking.transaction_service.DTO.TransactionRequest;

import com.banking.transaction_service.client.AccountClient;
import com.banking.transaction_service.entity.Transaction;
import com.banking.events.TransferFailedEvent;
import com.banking.events.TransactionCompletedEvent;
import com.banking.transaction_service.enums.TransactionType;
import com.banking.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final KafkaProducerService kafkaProducerService;

    public Transaction processTransaction(TransactionRequest request) {
        double amount = request.getAmount();
        TransactionType type = request.getType();

        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

        switch (type) {
            case DEPOSIT -> accountClient.adjustBalance(request.getSourceAccountId(), amount);

            case WITHDRAWAL -> {
                double currentBalance = accountClient.getBalance(request.getSourceAccountId());
                if (currentBalance < amount) throw new RuntimeException("Insufficient funds");
                accountClient.adjustBalance(request.getSourceAccountId(), -amount);
            }

            case TRANSFER -> {
                double sourceBalance = accountClient.getBalance(request.getSourceAccountId());
                if (sourceBalance < amount) {
                    TransferFailedEvent event = new TransferFailedEvent(
                            request.getSourceAccountId(),
                            request.getRecipient(),
                            "Insufficient funds"
                    );
                    kafkaProducerService.publishTransferFailed(event);
                    throw new RuntimeException("Insufficient funds");
                }
                accountClient.adjustBalance(request.getSourceAccountId(), -amount);
                accountClient.adjustBalance(request.getDestinationAccountId(), amount);
            }
        }

        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(request.getSourceAccountId());
        transaction.setDestinationAccountId(request.getDestinationAccountId());
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(request.getDescription());

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionCompletedEvent event = new TransactionCompletedEvent(
                request.getSourceAccountId(),
                request.getRecipient(),
                amount,
                generateSuccessMessage(type, amount, request.getDestinationAccountId())
        );
        kafkaProducerService.publishTransactionCompleted(event);

        return savedTransaction;
    }

    private String generateSuccessMessage(TransactionType type, double amount, Long destinationId) {
        return switch (type) {
            case DEPOSIT -> "Your deposit of ₹" + amount + " was successful.";
            case WITHDRAWAL -> "Your withdrawal of ₹" + amount + " was successful.";
            case TRANSFER -> "Transfer of ₹" + amount + " to account " + destinationId + " completed.";
        };
    }
}
