package com.banking.user_account_service.service;

import com.banking.user_account_service.entity.Account;
import com.banking.user_account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

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
//    public Account deposit(Long accountId,double amount){
//        Account account = getAccountById(accountId);
//        account.setBalance(amount+account.getBalance());
//        Account updated=accountRepository.save(account);
//        kafkaTemplate.send("account.updated","Deposit to"+account.getAccountNumber());
//        return updated;
//    }
//    public Account withdrawal(Long accountId, double amount){
//        Account account=getAccountById(accountId);
//        if(account.getBalance()<amount){
//            throw new RuntimeException("Insufficient funds");
//        }
//        account.setBalance(account.getBalance()-amount);
//        Account updated=accountRepository.save(account);
//        kafkaTemplate.send("account.updated","Withdrawal to"+account.getAccountNumber());
//        return updated;
//
//
//    }
public Account updateBalance(Long accountId, double newBalance) {
    Account account = getAccountById(accountId);
    account.setBalance(newBalance);
    return accountRepository.save(account);
}

}
