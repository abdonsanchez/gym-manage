package com.gym.booking.service;

import com.gym.booking.model.Booking;
import com.gym.booking.model.ClassSchedule;
import com.gym.booking.repository.BookingRepository;
import com.gym.booking.repository.ClassRepository;
import com.gym.booking.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Booking createBooking(Long memberId, Long scheduleId, LocalDate date) {
        ClassSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        // Check capacity
        long currentBookings = bookingRepository.countByScheduleIdAndBookingDateAndStatus(
                scheduleId, date, Booking.Status.confirmed);

        if (currentBookings >= schedule.getClassDefinition().getCapacity()) {
            throw new IllegalStateException("Class is full");
        }

        Booking booking = Booking.builder()
                .memberId(memberId)
                .schedule(schedule)
                .bookingDate(date)
                .status(Booking.Status.confirmed)
                .build();

        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> getMemberBookings(Long memberId) {
        return bookingRepository.findByMemberId(memberId);
    }
}
