package com.banking.transaction_service.controller;

import com.banking.transaction_service.DTO.TransactionRequest;
import com.banking.transaction_service.entity.Transaction;
import com.banking.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author Keerthana
 **/
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> process(@RequestBody TransactionRequest request,
                                               @RequestHeader("Authorization") String authHeader) {
        Transaction transaction = transactionService.processTransaction(request,authHeader);
        return ResponseEntity.ok(transaction);
    }
}
