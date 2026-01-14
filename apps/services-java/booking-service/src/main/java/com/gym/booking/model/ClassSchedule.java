package com.gym.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "class_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @NotNull
    private ClassDefinition classDefinition;

    @Column(name = "day_of_week", nullable = false)
    @NotNull
    private Integer dayOfWeek;

    @Column(name = "start_time", nullable = false)
    @NotNull
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull
    private LocalTime endTime;

    @Builder.Default
    @Column(name = "is_recurring")
    private Boolean isRecurring = true;
}
