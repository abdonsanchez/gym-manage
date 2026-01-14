package com.gym.access.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    @NotNull
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_method")
    private AccessMethod accessMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType;

    private String location;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "device_id")
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AccessStatus status;

    @Column(name = "denial_reason")
    private String denialReason;

    public enum AccessMethod {
        qr, rfid, facial
    }

    public enum AccessType {
        check_in, check_out
    }

    public enum AccessStatus {
        success, denied
    }

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
