package com.banking.notification_service.DTO;

import com.banking.notification_service.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Keerthana
 **/
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class NotificationResponse {
    private NotificationStatus status;
    private Long notificationId;
    private String message;
}
