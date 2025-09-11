package com.banking.user_account_service.service;

import com.banking.events.UserRegisteredEvent;

import com.banking.user_account_service.entity.Account;
import com.banking.user_account_service.entity.User;
import com.banking.user_account_service.enums.AccountStatus;
import com.banking.user_account_service.repository.AccountRepository;
import com.banking.user_account_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Keerthana
 **/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
//    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProducerService kafkaProducerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User registerUser(User request){
        request.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        User savedUser=userRepository.save(request);

        Account account=new Account();
        account.setAccountNumber(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
        account.setBalance(0.0);
        account.setUser(savedUser);
        account.setAccountType(request.getAccountType());
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);

//        kafkaTemplate.send("user.registered",savedUser.getEmail());
        UserRegisteredEvent event = new UserRegisteredEvent(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                account.getAccountNumber(),
                account.getAccountType().name()
        );
        kafkaProducerService.publishUserRegistered(event);

        return savedUser;
    }
    public String authenticateUser(String email,String password){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
//        return "jwt token for"+user.getEmail();
        return jwtService.generateToken(user.getId(), user.getEmail(), "CUSTOMER");
    }
}
