package com.gym.booking.service;

import com.gym.booking.model.Booking;
import com.gym.booking.model.ClassDefinition;
import com.gym.booking.model.ClassSchedule;
import com.gym.booking.repository.BookingRepository;
import com.gym.booking.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private BookingService bookingService;

    private ClassSchedule schedule;
    private Booking booking;

    @BeforeEach
    void setUp() {
        ClassDefinition def = new ClassDefinition();
        def.setCapacity(10);

        schedule = new ClassSchedule();
        schedule.setId(1L);
        schedule.setClassDefinition(def);

        booking = new Booking();
        booking.setId(1L);
        booking.setMemberId(101L);
        booking.setStatus(Booking.Status.confirmed);
    }

    @Test
    void createBooking_Success() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(bookingRepository.countByScheduleIdAndBookingDateAndStatus(anyLong(), any(), any()))
                .thenReturn(5L);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking created = bookingService.createBooking(101L, 1L, LocalDate.now());

        assertNotNull(created);
        assertEquals(Booking.Status.confirmed, created.getStatus());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void createBooking_ClassFull_ThrowsException() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(bookingRepository.countByScheduleIdAndBookingDateAndStatus(anyLong(), any(), any()))
                .thenReturn(10L); // Capacity is 10

        assertThrows(IllegalStateException.class,
                () -> bookingService.createBooking(101L, 1L, LocalDate.now()));

        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBooking_ScheduleNotFound_ThrowsException() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> bookingService.createBooking(101L, 1L, LocalDate.now()));
    }
}
