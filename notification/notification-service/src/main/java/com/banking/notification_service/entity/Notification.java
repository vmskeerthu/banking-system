package com.banking.notification_service.entity;

import com.banking.notification_service.enums.NotificationStatus;
import com.banking.notification_service.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Message cannot be blank")
    @Size(max = 500, message = "Message cannot exceed 500 characters")
    @Column(nullable = false, length = 500)
    private String message;

    @NotBlank(message = "Recipient is required")
    @Size(max = 150, message = "Recipient cannot exceed 150 characters")
    @Column(nullable = false, length = 150)
    private String recipient;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private Long userId;

    @NotNull(message = "Notification type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationType type;

    @NotNull(message = "Notification status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationStatus notificationStatus;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
