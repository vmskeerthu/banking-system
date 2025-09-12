package com.banking.user_account_service.controller;

import com.banking.user_account_service.DTO.AccountResponse;
import com.banking.user_account_service.DTO.AuthResponse;
import com.banking.user_account_service.DTO.BalanceUpdateRequest;
import com.banking.user_account_service.DTO.LoginRequest;
import com.banking.user_account_service.entity.Account;
import com.banking.user_account_service.entity.User;
import com.banking.user_account_service.repository.AccountRepository;
import com.banking.user_account_service.service.AccountService;
import com.banking.user_account_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Keerthana
 **/
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserService userService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody User request){
        User createdUser=userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PostMapping("/users/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        String jwtToken=userService.authenticateUser(loginRequest.getEmail(),loginRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
    @GetMapping("/accounts/{accountNumber}/balance")
    public ResponseEntity<AccountResponse> getAccountBalance(@PathVariable String accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        AccountResponse response = new AccountResponse(account.getAccountNumber(), account.getBalance());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/accounts/{accountNumber}/user-id")
    public ResponseEntity<Long> getUserIdByAccountNumber(@PathVariable String accountNumber) {
        Long userId = accountService.getUserIdByAccountNumber(accountNumber);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable String accountNumber){
        Account account=accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(account);
    }
    @PutMapping("/accounts/{accountNumber}/balance")
    public ResponseEntity<Account> updateBalance(@PathVariable String accountNumber, @RequestBody BalanceUpdateRequest request) {
        System.out.println("Updating balance for accountNumber={} with delta={}"+ accountNumber+ request.getDelta());

        Account updatedAccount = accountService.updateBalanceByNumber(accountNumber,request.getDelta());
        return ResponseEntity.ok(updatedAccount);
    }


    @GetMapping("users/email-by-account/{accountNumber}")
    public ResponseEntity<?> getEmailByAccountNumber(@PathVariable String accountNumber) {
        try {
            Account account = accountService.getAccountByNumber(accountNumber);
            String email = account.getUser().getEmail();
            return ResponseEntity.ok(Map.of("email", email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }


}
