package com.gym.analytics.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "retention_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetentionStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate month;

    @Column(name = "total_members_start")
    private Integer totalMembersStart;

    @Column(name = "new_members")
    private Integer newMembers;

    @Column(name = "churned_members")
    private Integer churnedMembers;

    @Column(name = "retention_rate")
    private BigDecimal retentionRate;

    @Column(name = "churn_rate")
    private BigDecimal churnRate;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;

    @PrePersist
    protected void onCreate() {
        calculatedAt = LocalDateTime.now();
    }
}
