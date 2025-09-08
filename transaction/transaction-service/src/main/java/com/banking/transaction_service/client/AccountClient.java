package com.banking.transaction_service.client;

import com.banking.transaction_service.DTO.AccountResponse;
import com.banking.transaction_service.DTO.BalanceUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Keerthana
 **/
@Component
@RequiredArgsConstructor
public class AccountClient {
    private final RestTemplate restTemplate;
    private final String accountServiceUrl = "http://user-account-service/api/accounts";

    public double getBalance(Long accountId) {
        AccountResponse response = restTemplate.getForObject(accountServiceUrl + "/" + accountId + "/balance", AccountResponse.class);
        return response.getBalance();
    }

    public void adjustBalance(Long accountId, double amount) {
        double currentBalance = getBalance(accountId);
        double newBalance = currentBalance + amount;
        BalanceUpdateRequest request=new  BalanceUpdateRequest(accountId,newBalance);
//        restTemplate.put(accountServiceUrl + "/" + accountId + "/balance?newBalance=" + newBalance, null, AccountResponse.class);
        restTemplate.put(accountServiceUrl + "/" + accountId + "/balance", request);
    }
}
