package com.banking.events;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class TransactionCompletedEvent {
    private Long userId;
    private String recipient;
    private double amount;
    private String message;
}