package com.banking.user_account_service.entity;

import com.banking.user_account_service.enums.AccountStatus;
import com.banking.user_account_service.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * @author Keerthana
 **/
@Entity
@Table(name="accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private double balance;
//    @Enumerated(EnumType.STRING)
    @NonNull
    private AccountType accountType; // SAVINGS, CURRENT, etc.

    @Enumerated(EnumType.STRING)
    private AccountStatus status; // ACTIVE, FROZEN, CLOSED

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

}
