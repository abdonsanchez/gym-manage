package com.gym.analytics.controller;

import com.gym.analytics.model.AttendanceMetric;
import com.gym.analytics.model.RevenueReport;
import com.gym.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceMetric>> getAttendance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(analyticsService.getAttendanceMetrics(start, end));
    }

    @PostMapping("/revenue/generate-mock")
    public ResponseEntity<RevenueReport> generateReport() {
        return ResponseEntity.ok(analyticsService.generateMockRevenueReport());
    }
}
