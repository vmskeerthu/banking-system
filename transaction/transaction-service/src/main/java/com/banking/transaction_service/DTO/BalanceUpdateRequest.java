package com.banking.transaction_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Keerthana
 **/
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class BalanceUpdateRequest {
//    private Long accountId;
//private String accountNumber;
    private double delta;
}
