package com.gym.booking.repository;

import com.gym.booking.model.Booking;
import com.gym.booking.model.ClassDefinition;
import com.gym.booking.model.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassDefinition, Long> {
}

@Repository
public interface ScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByClassDefinitionId(Long classId);
}

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByMemberId(Long memberId);

    long countByScheduleIdAndBookingDateAndStatus(Long scheduleId, LocalDate bookingDate, Booking.Status status);
}
