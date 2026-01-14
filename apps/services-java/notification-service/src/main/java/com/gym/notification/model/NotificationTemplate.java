package com.gym.notification.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "notification_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Builder.Default
    private String language = "es";

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    public enum NotificationType {
        email, push, sms
    }
}
