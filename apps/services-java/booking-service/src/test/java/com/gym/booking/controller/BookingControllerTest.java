package com.gym.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.booking.model.Booking;
import com.gym.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void bookClass_Success() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingService.createBooking(eq(1L), eq(1L), any(LocalDate.class)))
                .thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                .param("memberId", "1")
                .param("scheduleId", "1")
                .param("date", "2024-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void getMyBookings_Success() throws Exception {
        when(bookingService.getMemberBookings(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/bookings/member/1"))
                .andExpect(status().isOk());
    }
}
