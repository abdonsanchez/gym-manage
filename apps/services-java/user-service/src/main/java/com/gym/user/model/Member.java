package com.gym.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa un miembro del gimnasio.
 */
@Entity
@Table(name = "members", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_document_id", columnList = "document_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "document_id", unique = true, nullable = false, length = 50)
    @NotBlank(message = "El documento de identidad es obligatorio")
    @Size(max = 50)
    private String documentId;
    
    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "profile_photo_url", length = 500)
    private String profilePhotoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_type_id", nullable = false)
    @NotNull
    private MembershipType membershipType;

    @Column(name = "membership_start_date", nullable = false)
    @NotNull
    private LocalDate membershipStartDate;

    @Column(name = "membership_end_date", nullable = false)
    @NotNull
    private LocalDate membershipEndDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.active;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalHistory medicalHistory;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EmergencyContact> emergencyContacts = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Gender {
        M, F, Other, PreferNotToSay
    }

    public enum Status {
        active, suspended, cancelled
    }
}
