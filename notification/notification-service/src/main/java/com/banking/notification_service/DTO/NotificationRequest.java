package com.banking.notification_service.DTO;

import com.banking.notification_service.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Keerthana
 **/
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class NotificationRequest {
    private String message;
    private String recipient;
    @NotNull
    private NotificationType type;
    private Long userId;

}
