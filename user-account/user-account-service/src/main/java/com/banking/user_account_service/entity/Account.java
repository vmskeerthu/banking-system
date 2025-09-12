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
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "accountNumber")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false, unique = true, length = 15)
//    private String accountNumber;
@Column(name = "account_number", nullable = false, unique = true, length = 15)
private String accountNumber;

    @Column(nullable = false)
    @jakarta.validation.constraints.DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private double balance;

    @Enumerated(EnumType.STRING)
    @NonNull
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id",nullable = false)
    private User user;

}
