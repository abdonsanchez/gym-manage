package com.gym.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad JPA para el historial médico de un miembro.
 */
@Entity
@Table(name = "medical_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    @ToString.Exclude
    private Member member;

    @Column(name = "has_heart_condition")
    private Boolean hasHeartCondition;

    @Column(name = "has_chest_pain")
    private Boolean hasChestPain;

    @Column(name = "has_balance_issues")
    private Boolean hasBalanceIssues;

    @Column(name = "current_medications", columnDefinition = "TEXT")
    private String currentMedications;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "injuries_history", columnDefinition = "TEXT")
    private String injuriesHistory;

    @Column(name = "medical_restrictions", columnDefinition = "TEXT")
    private String medicalRestrictions;

    @Column(name = "staff_notes", columnDefinition = "TEXT")
    private String staffNotes;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
