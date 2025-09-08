package com.banking.notification_service.service;

import com.banking.notification_service.DTO.NotificationRequest;
import com.banking.notification_service.DTO.NotificationResponse;
import com.banking.notification_service.Repository.NotificationRepository;
import com.banking.notification_service.entity.Notification;
import com.banking.notification_service.enums.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Keerthana
 **/
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public NotificationResponse sendNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setRecipient(request.getRecipient());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setNotificationStatus(NotificationStatus.PENDING);
        notification.setTimestamp(LocalDateTime.now());

        try {
            if (request.getType().name().equals("EMAIL")) {
                emailService.sendEmail(request.getRecipient(), "Bank Notification", request.getMessage());
                System.out.println("email triggered");
            }
            // Add SMS or PUSH logic here later
            notification.setNotificationStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            notification.setNotificationStatus(NotificationStatus.FAILED);
        }

        Notification saved = notificationRepository.save(notification);
        return new NotificationResponse(
                saved.getNotificationStatus(),
                saved.getId(),
                saved.getMessage()
        );
    }
    }

