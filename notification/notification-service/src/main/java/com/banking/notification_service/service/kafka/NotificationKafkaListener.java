package com.banking.notification_service.service.kafka;

import com.banking.notification_service.DTO.NotificationRequest;
import com.banking.events.TransferFailedEvent;
import com.banking.events.TransactionCompletedEvent;
import com.banking.events.UserRegisteredEvent;
import com.banking.notification_service.enums.NotificationType;
import com.banking.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Keerthana
 **/
@Service
@RequiredArgsConstructor
public class NotificationKafkaListener {
    private final NotificationService notificationService;

    @KafkaListener(topics = "transaction-completed", groupId = "notification-group")
    public void onTransactionCompleted(TransactionCompletedEvent event) {
        NotificationRequest request = new NotificationRequest(
                event.getMessage(),
                event.getRecipient(),
                NotificationType.EMAIL,
                event.getUserId()
        );
        notificationService.sendNotification(request);
    }

    @KafkaListener(topics = "user-registered", groupId = "notification-group")
    public void onUserRegistered(UserRegisteredEvent event) {
        String message = "Welcome " + event.getName() + "! Your account has been successfully created.";
        NotificationRequest request = new NotificationRequest(
                message,
                event.getEmail(),
                NotificationType.EMAIL,
                event.getUserId()
        );
        notificationService.sendNotification(request);
    }

    @KafkaListener(topics = "transfer-failed", groupId = "notification-group")
    public void onTransferFailed(TransferFailedEvent event) {
        String message = "Transfer failed: " + event.getReason();
        NotificationRequest request = new NotificationRequest(
                message,
                event.getRecipient(),
                NotificationType.EMAIL,
                event.getUserId()
        );
        notificationService.sendNotification(request);
    }
}
