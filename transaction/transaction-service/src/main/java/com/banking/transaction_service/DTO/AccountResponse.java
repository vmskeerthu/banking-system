package com.banking.transaction_service.DTO;

import lombok.*;

/**
 * @author Keerthana
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long accountId;
    private double balance;
}
