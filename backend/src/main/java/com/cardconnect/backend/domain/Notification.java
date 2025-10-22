package com.cardconnect.backend.domain;

import jakarta.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // or use @ManyToOne if you want to link to User entity
    private String title;
    private String message;
    private String time; // or use LocalDateTime and format on frontend
    private boolean isRead = false;
    private String sender;
    private String recipient;

    @Enumerated(EnumType.STRING)
    private NotificationType type; // Store enum as text

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return isRead;
    }

    public Notification setRead(boolean read) {
        isRead = read;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public Notification setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getRecipient() {
        return recipient;
    }

    public Notification setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public NotificationType getType() {
        return type;
    }

    public Notification setType(NotificationType type) {
        this.type = type;
        return this;
    }

    // ===== Enum Declaration =====
    public enum NotificationType {
        INFO,        // General information
        ALERT,       // Urgent warning or issue
        REMINDER,    // Event or deadline reminder
        SYSTEM,      // System or maintenance notification
        MESSAGE,     // Direct user message
        UPDATE       // Application or account update
    }
}
