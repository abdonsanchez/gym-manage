package com.gym.booking.controller;

import com.gym.booking.model.Booking;
import com.gym.booking.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> bookClass(
            @RequestParam Long memberId,
            @RequestParam Long scheduleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.createBooking(memberId, scheduleId, date));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Booking>> getMyBookings(@PathVariable Long memberId) {
        return ResponseEntity.ok(bookingService.getMemberBookings(memberId));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleConflict(IllegalStateException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }
}
