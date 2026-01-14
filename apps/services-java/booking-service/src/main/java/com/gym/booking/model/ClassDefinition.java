package com.gym.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private DifficultyLevel difficultyLevel;

    @Min(1)
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Min(1)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Trainer instructor;

    @Column(name = "room_id")
    private Long roomId;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    public enum DifficultyLevel {
        beginner, intermediate, advanced
    }
}
