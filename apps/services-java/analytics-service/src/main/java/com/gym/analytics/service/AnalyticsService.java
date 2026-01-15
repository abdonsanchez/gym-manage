package com.gym.analytics.service;

import com.gym.analytics.model.AttendanceMetric;
import com.gym.analytics.model.RevenueReport;
import com.gym.analytics.repository.AttendanceMetricRepository;
import com.gym.analytics.repository.RevenueReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para el análisis de datos y generación de reportes.
 */
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AttendanceMetricRepository attendanceRepository;
    private final RevenueReportRepository revenueRepository;

    /**
     * Recupera métricas de asistencia filtradas por fecha.
     *
     * @param start Fecha inicio del filtro.
     * @param end   Fecha fin del filtro.
     * @return Lista de métricas.
     */
    public List<AttendanceMetric> getAttendanceMetrics(LocalDate start, LocalDate end) {
        return attendanceRepository.findByDateBetween(start, end);
    }

    /**
     * Crea un reporte de ganancias simulado para propósitos de demostración.
     *
     * @return Reporte financiero guardado.
     */
    public RevenueReport generateMockRevenueReport() {
        // Mock logic for demo purposes
        RevenueReport report = RevenueReport.builder()
                .periodStart(LocalDate.now().minusMonths(1))
                .periodEnd(LocalDate.now())
                .totalRevenue(new BigDecimal("15000.00"))
                .totalTransactions(350)
                .averageTransaction(new BigDecimal("42.85"))
                .paymentMethodBreakdown("{\"credit_card\": 300, \"cash\": 50}")
                .build();

        return revenueRepository.save(report);
    }
}
