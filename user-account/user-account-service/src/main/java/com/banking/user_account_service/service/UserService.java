package com.banking.user_account_service.service;

import com.banking.events.UserRegisteredEvent;

import com.banking.user_account_service.entity.Account;
import com.banking.user_account_service.entity.User;
import com.banking.user_account_service.enums.AccountStatus;
import com.banking.user_account_service.repository.AccountRepository;
import com.banking.user_account_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
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

    public User registerUser(User request){
        User savedUser=userRepository.save(request);

        Account account=new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setBalance(0.0);
        account.setUser(savedUser);
        account.setAccountType(request.getAccountType());
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);

//        kafkaTemplate.send("user.registered",savedUser.getEmail());
        UserRegisteredEvent event = new UserRegisteredEvent(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName()
        );
        kafkaProducerService.publishUserRegistered(event);

        return savedUser;
    }
    public String authenticateUser(String email,String password){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        if (!user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return "jwt token for"+user.getEmail();
    }
}
