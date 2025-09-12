//package com.banking.transaction_service.service;
//
//import com.banking.transaction_service.DTO.TransactionRequest;
//
//import com.banking.transaction_service.client.AccountClient;
//import com.banking.transaction_service.entity.Transaction;
//import com.banking.events.TransferFailedEvent;
//import com.banking.events.TransactionCompletedEvent;
//import com.banking.transaction_service.enums.TransactionType;
//import com.banking.transaction_service.repository.TransactionRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class TransactionService {
//    private final TransactionRepository transactionRepository;
//    private final AccountClient accountClient;
//    private final HttpServletRequest httpServletRequest;
//    private final KafkaProducerService kafkaProducerService;
//    // 🔹 Get JWT from incoming request
//
//    // 🔹 Fetch email internally using account number
//
//    public Transaction processTransaction(TransactionRequest request, String authHeader) {
//        double amount = request.getAmount();
//        TransactionType type = request.getType();
//
//        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
//
//        switch (type) {
//            case DEPOSIT -> {
//                System.out.println("Token being sent: " + authHeader);
//                accountClient.adjustBalance(request.getSourceAccountNumber(), amount, authHeader);
//            }
//
//            case WITHDRAWAL -> {
//                double currentBalance = accountClient.getBalance(request.getSourceAccountNumber(), authHeader);
//                if (currentBalance < amount) throw new RuntimeException("Insufficient funds");
//                accountClient.adjustBalance(request.getSourceAccountNumber(), -amount, authHeader);
//            }
//
//            case TRANSFER -> {
//                double sourceBalance = accountClient.getBalance(request.getSourceAccountNumber(), authHeader);
//                if (sourceBalance < amount) {
//                    Long userId = accountClient.getUserIdByAccountNumber(request.getSourceAccountNumber(), authHeader);
//
//                    TransferFailedEvent event = new TransferFailedEvent(
//                            userId,
//                            accountClient.getEmailByAccountNumber(request.getSourceAccountNumber(), authHeader),
//                            "Insufficient funds"
//                    );
//                    kafkaProducerService.publishTransferFailed(event);
//                    throw new RuntimeException("Insufficient funds");
//                }
//                accountClient.adjustBalance(request.getSourceAccountNumber(), -amount, authHeader);
//                accountClient.adjustBalance(request.getDestinationAccountNumber(), amount, authHeader);
//            }
//        }
//
//        Transaction transaction = new Transaction();
//        transaction.setSourceAccountNumber(request.getSourceAccountNumber());
//        transaction.setDestinationAccountNumber(request.getDestinationAccountNumber());
//        transaction.setAmount(amount);
//        transaction.setType(type);
//        transaction.setTimestamp(LocalDateTime.now());
//        transaction.setDescription(request.getDescription());
//
//        Transaction savedTransaction = transactionRepository.save(transaction);
//        Long userId = accountClient.getUserIdByAccountNumber(request.getSourceAccountNumber(), authHeader);
//        TransactionCompletedEvent event = new TransactionCompletedEvent(
//                userId,
//                accountClient.getEmailByAccountNumber(request.getSourceAccountNumber(), authHeader),
//                amount,
//                generateSuccessMessage(type, amount, request.getDestinationAccountNumber())
//        );
//        kafkaProducerService.publishTransactionCompleted(event);
//
//        return savedTransaction;
//    }
//
//    private String generateSuccessMessage(TransactionType type, double amount, String destinationId) {
//        return switch (type) {
//            case DEPOSIT -> "Your deposit of ₹" + amount + " was successful.";
//            case WITHDRAWAL -> "Your withdrawal of ₹" + amount + " was successful.";
//            case TRANSFER -> "Transfer of ₹" + amount + " to account " + destinationId + " completed.";
//        };
//    }
//}
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

    public Transaction processTransaction(TransactionRequest request, String authHeader) {
        double amount = request.getAmount();
        TransactionType type = request.getType();

        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

        switch (type) {
            case DEPOSIT -> accountClient.adjustBalance(request.getSourceAccountNumber(), amount, authHeader);

            case WITHDRAWAL -> {
                double currentBalance = accountClient.getBalance(request.getSourceAccountNumber(), authHeader);
                if (currentBalance < amount) throw new RuntimeException("Insufficient funds");
                accountClient.adjustBalance(request.getSourceAccountNumber(), -amount, authHeader);
            }

            case TRANSFER -> {
                double sourceBalance = accountClient.getBalance(request.getSourceAccountNumber(), authHeader);
                if (sourceBalance < amount) {
                    Long userId = accountClient.getUserIdByAccountNumber(request.getSourceAccountNumber(), authHeader);

                    TransferFailedEvent event = new TransferFailedEvent(
                            userId,
                            request.getRecipient(),
                            "Insufficient funds"
                    );
                    kafkaProducerService.publishTransferFailed(event);
                    throw new RuntimeException("Insufficient funds");
                }
                accountClient.adjustBalance(request.getSourceAccountNumber(), -amount, authHeader);
                accountClient.adjustBalance(request.getDestinationAccountNumber(), amount, authHeader);
            }
        }

        Transaction transaction = new Transaction();
        transaction.setSourceAccountNumber(request.getSourceAccountNumber());
        transaction.setDestinationAccountNumber(request.getDestinationAccountNumber());
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(request.getDescription());

        Transaction savedTransaction = transactionRepository.save(transaction);
        Long userId = accountClient.getUserIdByAccountNumber(request.getSourceAccountNumber(), authHeader);

        String email = accountClient.getEmailByAccountNumber(request.getSourceAccountNumber(), authHeader);

        TransactionCompletedEvent event = new TransactionCompletedEvent(
                userId,
                email, // dynamically fetched
                amount,
                generateSuccessMessage(type, amount, request.getDestinationAccountNumber())
        );
        kafkaProducerService.publishTransactionCompleted(event);

        return savedTransaction;
    }

    private String generateSuccessMessage(TransactionType type, double amount, String destinationId) {
        return switch (type) {
            case DEPOSIT -> "Your deposit of ₹" + amount + " was successful.";
            case WITHDRAWAL -> "Your withdrawal of ₹" + amount + " was successful.";
            case TRANSFER -> "Transfer of ₹" + amount + " to account " + destinationId + " completed.";
        };
    }
}