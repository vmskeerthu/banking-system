package com.banking.user_account_service.DTO;

import lombok.*;

/**
 * @author Keerthana
 **/
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class BalanceUpdateRequest {
//    private String accountNumber;
    private double delta;
}
