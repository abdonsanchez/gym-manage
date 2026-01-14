package com.gym.analytics.repository;

import com.gym.analytics.model.AttendanceMetric;
import com.gym.analytics.model.RetentionStats;
import com.gym.analytics.model.RevenueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceMetricRepository extends JpaRepository<AttendanceMetric, Long> {
    List<AttendanceMetric> findByDateBetween(LocalDate startDate, LocalDate endDate);
}

@Repository
public interface RevenueReportRepository extends JpaRepository<RevenueReport, Long> {
    List<RevenueReport> findByPeriodStartAfter(LocalDate date);
}

@Repository
public interface RetentionStatsRepository extends JpaRepository<RetentionStats, Long> {
}
