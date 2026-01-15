package com.gym.booking.controller;

import com.gym.booking.model.Booking;
import com.gym.booking.service.BookingService;
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
 * Controlador REST para la gestión de reservas de clases y servicios.
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Endpoints para la gestión de reservas")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Crea una nueva reserva para un miembro en una clase específica.
     *
     * @param memberId   ID del miembro que realiza la reserva.
     * @param scheduleId ID del horario/clase a reservar.
     * @param date       Fecha de la reserva.
     * @return ResponseEntity con la reserva creada.
     */
    @Operation(summary = "Crear reserva", description = "Permite a un miembro reservar una clase en una fecha específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de reserva (cupo lleno o ya reservado)", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Booking> bookClass(
            @Parameter(description = "ID del miembro", required = true) @RequestParam Long memberId,
            @Parameter(description = "ID del horario", required = true) @RequestParam Long scheduleId,
            @Parameter(description = "Fecha de la reserva (YYYY-MM-DD)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.createBooking(memberId, scheduleId, date));
    }

    /**
     * Obtiene el historial de reservas de un miembro.
     *
     * @param memberId ID del miembro.
     * @return Lista de reservas.
     */
    @Operation(summary = "Obtener reservas de miembro", description = "Devuelve el historial de reservas de un miembro específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)))
    })
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Booking>> getMyBookings(
            @Parameter(description = "ID del miembro", required = true) @PathVariable Long memberId) {
        return ResponseEntity.ok(bookingService.getMemberBookings(memberId));
    }

    /**
     * Maneja conflictos de estado (ej. cupo lleno).
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleConflict(IllegalStateException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }
}
