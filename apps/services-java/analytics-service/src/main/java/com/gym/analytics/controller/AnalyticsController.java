package com.gym.analytics.controller;

import com.gym.analytics.model.AttendanceMetric;
import com.gym.analytics.model.RevenueReport;
import com.gym.analytics.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para la visualización de métricas y reportes.
 */
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Endpoints para métricas y reportes de negocio")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * Obtiene métricas de asistencia en un rango de fechas.
     *
     * @param start Fecha de inicio.
     * @param end   Fecha de fin.
     * @return Lista de métricas de asistencia.
     */
    @Operation(summary = "Métricas de asistencia", description = "Devuelve estadísticas de afluencia en un rango de fechas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Métricas recuperadas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceMetric.class)))
    })
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceMetric>> getAttendance(
            @Parameter(description = "Fecha inicial (YYYY-MM-DD)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @Parameter(description = "Fecha final (YYYY-MM-DD)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(analyticsService.getAttendanceMetrics(start, end));
    }

    /**
     * Genera un reporte de ingresos simulado (demo).
     *
     * @return Reporte de ingresos.
     */
    @Operation(summary = "Generar reporte ingresos", description = "Genera un reporte financiero simulado para demostración.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RevenueReport.class)))
    })
    @PostMapping("/revenue/generate-mock")
    public ResponseEntity<RevenueReport> generateReport() {
        return ResponseEntity.ok(analyticsService.generateMockRevenueReport());
    }
}
