package com.cardconnect.backend.controller;

import com.cardconnect.backend.domain.Notification;
import com.cardconnect.backend.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = {"http://localhost:3000"})
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    // 🔹 Create a new notification
    @PostMapping("/create")
    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.saveNotification(notification);
        return ResponseEntity.ok(savedNotification);
    }

    // 🔹 Get all notifications
    @GetMapping("/getAll")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // 🔹 Get notifications by recipient
    @GetMapping("/recipient/{recipient}")
    public ResponseEntity<List<Notification>> getByRecipient(@PathVariable String recipient) {
        return ResponseEntity.ok(notificationService.getNotificationsByRecipient(recipient));
    }

    // 🔹 Get notifications by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Notification>> getByType(@PathVariable Notification.NotificationType type) {
        return ResponseEntity.ok(notificationService.getNotificationsByType(type));
    }

    // 🔹 Get a single notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return notification != null ? ResponseEntity.ok(notification) : ResponseEntity.notFound().build();
    }

    // 🔹 Delete a notification by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Mark a notification as read
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification updated = notificationService.markAsRead(id);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // 🔹 Get notifications for the currently authenticated user
    @GetMapping("/me")
    public ResponseEntity<List<Notification>> getUserNotifications(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        return ResponseEntity.ok(notificationService.getNotificationsByRecipient(username));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

}
