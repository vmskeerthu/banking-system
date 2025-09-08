package com.banking.user_account_service.service;

/**
 * @author Keerthana
 **/

import com.banking.events.UserRegisteredEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserRegistered(UserRegisteredEvent event) {
        kafkaTemplate.send("user-registered", event);
    }
}
