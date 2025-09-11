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
      try{  NotificationRequest request = new NotificationRequest(
                event.getMessage(),
                event.getRecipient(),
                NotificationType.EMAIL,
                event.getUserId()
        );
        notificationService.sendNotification(request);}catch (Exception e){
          System.out.println("Failed to send transaction completed notification ");
      }
    }

    @KafkaListener(topics = "user-registered", groupId = "notification-group")
    public void onUserRegistered(UserRegisteredEvent event) {
      try{  String maskedAccountNumber = maskAccountNumber(event.getAccountNumber());
        String message = String.format(
                "Dear %s,\n\n" +
                        "Welcome to MyBank! Your account has been successfully created.\n\n" +
                        "Account Number: %s\n" +
                        "Account Type: %s\n" +
                        "Status: Active\n\n" +
                        "You can now log in to our online banking portal to view your balance, make transactions, and manage your account.\n\n" +
                        "Thank you for choosing us.\n\n" +
                        "Warm regards,\n" +
                        "MyBank Customer Care",
                event.getName(),
                maskedAccountNumber,
                event.getAccountType()
        );

        NotificationRequest request = new NotificationRequest(
                message,
                event.getEmail(),
                NotificationType.EMAIL,
                event.getUserId()
        );
        notificationService.sendNotification(request);}catch (Exception e){
          System.out.println("Failed to process user-registered event for user {}"+event.getUserId()+ e);
      }
    }
    private String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() <= 4) return accountNumber;
        return "XXXX-XXXX-" + accountNumber.substring(accountNumber.length() - 4);
    }

    @KafkaListener(topics = "transfer-failed", groupId = "notification-group")
    public void onTransferFailed(TransferFailedEvent event) {
       try{ String message = "Transfer failed: " + event.getReason();
        NotificationRequest request = new NotificationRequest(
                message,
                event.getRecipient(),
                NotificationType.EMAIL,
                event.getUserId()
        );
        notificationService.sendNotification(request);}catch (Exception e){
           System.out.println("Transaction failed");
       }
    }
}
