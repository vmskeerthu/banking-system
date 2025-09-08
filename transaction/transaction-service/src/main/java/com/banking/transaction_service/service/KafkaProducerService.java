package com.banking.transaction_service.service;

import com.banking.events.TransferFailedEvent;
import com.banking.events.TransactionCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Keerthana
 **/
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTransactionCompleted(TransactionCompletedEvent event) {
        kafkaTemplate.send("transaction-completed", event);
    }
    public void publishTransferFailed(TransferFailedEvent event) {
        kafkaTemplate.send("transfer-failed", event);
    }

}
