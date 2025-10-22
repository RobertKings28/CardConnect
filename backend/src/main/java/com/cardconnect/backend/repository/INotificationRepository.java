package com.cardconnect.backend.repository;

import com.cardconnect.backend.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    // 🔹 Find notifications by recipient
    List<Notification> findByRecipient(String recipient);

    // 🔹 Find notifications by type (INFO, ALERT, etc.)
    List<Notification> findByType(Notification.NotificationType type);
}
