package com.banking.transaction_service.DTO;

import com.banking.transaction_service.enums.TransactionType;
import lombok.*;

/**
 * @author Keerthana
 **/
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class TransactionRequest {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private double amount;
    private TransactionType type;
    private String description;
    private String recipient; // email or phone for notification

}
