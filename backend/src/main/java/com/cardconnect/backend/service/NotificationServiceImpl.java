package com.cardconnect.backend.service;

import com.cardconnect.backend.domain.Notification;
import com.cardconnect.backend.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // 🔹 Create or update a notification
    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // 🔹 Get all notifications
    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // 🔹 Get a notification by ID
    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Notification> getNotificationsByUserId(String userId) {
        // Filter notifications: show if recipient is "ALL" OR recipient matches userId
        return notificationRepository.findAll()
                .stream()
                .filter(n -> "ALL".equalsIgnoreCase(n.getRecipient()) || userId.equals(n.getRecipient()))
                .toList();
    }

    // 🔹 Delete notification by ID
    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    // 🔹 Get notifications by recipient
    @Override
    public List<Notification> getNotificationsByRecipient(String recipient) {
        return notificationRepository.findByRecipient(recipient);
    }

    // 🔹 Get notifications by type
    @Override
    public List<Notification> getNotificationsByType(Notification.NotificationType type) {
        return notificationRepository.findByType(type);
    }

    // 🔹 Mark a notification as read
    @Override
    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null && !notification.isRead()) {
            notification.setRead(true);
            return notificationRepository.save(notification);
        }
        return notification;
    }
}
