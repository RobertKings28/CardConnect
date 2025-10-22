package com.cardconnect.backend.service;

import com.cardconnect.backend.domain.Notification;
import java.util.List;

public interface INotificationService {

    // 🔹 Basic CRUD operations
    Notification saveNotification(Notification notification);
    List<Notification> getAllNotifications();
    Notification getNotificationById(Long id);
    void deleteNotification(Long id);

    // 🔹 Custom finders
    List<Notification> getNotificationsByRecipient(String recipient);
    List<Notification> getNotificationsByType(Notification.NotificationType type);
    List<Notification> getNotificationsByUserId(String userId);

    // 🔹 Mark as read
    Notification markAsRead(Long id);
}
