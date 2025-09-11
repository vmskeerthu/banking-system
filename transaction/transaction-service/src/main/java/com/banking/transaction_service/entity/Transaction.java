package com.banking.transaction_service.entity;

/**
 * @author Keerthana
 **/

import com.banking.transaction_service.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = true)
//    private Long sourceAccountId;
//
//    @Column(nullable = true)
//    private Long destinationAccountId;

@Column(length = 15,nullable = true)
private String sourceAccountNumber;

    @Column(length = 15, nullable = true)
    private String destinationAccountNumber;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than zero")
    private double amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction type is required")
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
