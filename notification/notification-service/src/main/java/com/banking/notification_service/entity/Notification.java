package com.banking.notification_service.entity;

import com.banking.notification_service.enums.NotificationStatus;
import com.banking.notification_service.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;

/**
 * @author Keerthana
 **/
@Entity
@Table(name="notifications")
@Getter @Setter
@NoArgsConstructor@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String recipient;
    private Long userId;

    private NotificationType type;
    private NotificationStatus notificationStatus;

    private LocalDateTime timestamp;

}
