package com.banking.user_account_service.controller;

import com.banking.user_account_service.DTO.AccountResponse;
import com.banking.user_account_service.DTO.BalanceUpdateRequest;
import com.banking.user_account_service.entity.Account;
import com.banking.user_account_service.entity.User;
import com.banking.user_account_service.service.AccountService;
import com.banking.user_account_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Keerthana
 **/
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserService userService;
    private final AccountService accountService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody User request){
        User createdUser=userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest){
        String jwtToken=userService.authenticateUser(loginRequest.getEmail(),loginRequest.getPasswordHash());
        return ResponseEntity.ok(jwtToken);
    }
    @GetMapping("/accounts/{accountId}/balance")
//    public ResponseEntity<Double> getAccountBalance(@PathVariable Long accountId) {
//        double balance = accountService.getAccountById(accountId).getBalance();
//        return ResponseEntity.ok(balance);
//    }
    public ResponseEntity<AccountResponse> getAccountBalance(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        AccountResponse response = new AccountResponse(account.getId(), account.getBalance());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable Long accountId){
        Account account=accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }
    @PutMapping("/accounts/{accountId}/balance")
    public ResponseEntity<Account> updateBalance(@RequestBody BalanceUpdateRequest request) {
        Account updatedAccount = accountService.updateBalance(request.getAccountId(),request.getNewBalance());
        return ResponseEntity.ok(updatedAccount);
    }
//    @PostMapping("/accounts/deposit")
//    public ResponseEntity<Account> deposit(@RequestBody TransactionRequest transactionRequest){
//        Account updatedAccount=accountService.deposit(transactionRequest.getAccountId(),transactionRequest.getAmount());
//        return ResponseEntity.ok(updatedAccount);
//    }
//    @PostMapping("/accounts/withdraw")
//    public ResponseEntity<Account> withdraw(@RequestBody TransactionRequest transactionRequest){
//        Account updatedAccount=accountService.withdrawal(transactionRequest.getAccountId(), transactionRequest.getAmount());
//        return ResponseEntity.ok(updatedAccount);
//    }

}
