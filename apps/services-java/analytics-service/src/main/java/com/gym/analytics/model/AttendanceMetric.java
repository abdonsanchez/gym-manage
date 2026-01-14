package com.gym.analytics.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "attendance_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer hour;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "total_checkins")
    private Integer totalCheckins;

    @Column(name = "unique_members")
    private Integer uniqueMembers;

    @Column(name = "average_stay_minutes")
    private Integer averageStayMinutes;

    private String location;
}
