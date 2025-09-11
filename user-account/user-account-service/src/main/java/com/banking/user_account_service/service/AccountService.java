package com.banking.user_account_service.service;

import com.banking.user_account_service.entity.Account;
import com.banking.user_account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Keerthana
 **/

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
//    private final KafkaTemplate<String, String> kafkaTemplate;
    public Account getAccountById(Long accountId){
        return accountRepository.findById(accountId).orElseThrow(()->new RuntimeException("Account not found"));
    }
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
    public Account updateBalanceById(Long accountId, double delta) {
        Account account = getAccountById(accountId);
        return applyBalanceChange(account, delta);
    }
@Transactional
    public Account updateBalanceByNumber(String accountNumber, double delta) {
        Account account = getAccountByNumber(accountNumber);
        return applyBalanceChange(account, delta);
    }
    public Long getUserIdByAccountNumber(String accountNumber) {
        return accountRepository.findUserIdByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found for account number: " + accountNumber));
    }


    private Account applyBalanceChange(Account account, double delta) {
        double newBalance = account.getBalance() + delta;
        if (newBalance < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

//    public Account updateBalance(Long accountId, double delta) {
//        Account account = getAccountById(accountId);
//        double newBalance = account.getBalance() + delta;
//
//        if (newBalance < 0) {
//            throw new RuntimeException("Insufficient funds");
//        }
//
//        account.setBalance(newBalance);
//        return accountRepository.save(account);
//    }
}
