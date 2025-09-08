package com.banking.notification_service.Repository;

import com.banking.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Keerthana
 **/

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
