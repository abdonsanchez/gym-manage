package com.gym.analytics.service;

import com.gym.analytics.model.AttendanceMetric;
import com.gym.analytics.model.RevenueReport;
import com.gym.analytics.repository.AttendanceMetricRepository;
import com.gym.analytics.repository.RevenueReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private AttendanceMetricRepository attendanceRepository;

    @Mock
    private RevenueReportRepository revenueRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void getAttendanceMetrics_Success() {
        when(attendanceRepository.findByDateBetween(any(), any()))
                .thenReturn(Collections.singletonList(new AttendanceMetric()));

        List<AttendanceMetric> result = analyticsService.getAttendanceMetrics(LocalDate.now(), LocalDate.now());

        assertFalse(result.isEmpty());
    }

    @Test
    void generateMockRevenueReport_Success() {
        when(revenueRepository.save(any(RevenueReport.class)))
                .thenAnswer(invocation -> {
                    RevenueReport report = invocation.getArgument(0);
                    report.setId(1L);
                    return report;
                });

        RevenueReport report = analyticsService.generateMockRevenueReport();

        assertNotNull(report);
        assertEquals(new BigDecimal("15000.00"), report.getTotalRevenue());
    }
}
