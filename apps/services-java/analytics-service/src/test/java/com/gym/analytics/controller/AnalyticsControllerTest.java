package com.gym.analytics.controller;

import com.gym.analytics.model.RevenueReport;
import com.gym.analytics.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnalyticsController.class)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    void getAttendance_Success() throws Exception {
        when(analyticsService.getAttendanceMetrics(any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/analytics/attendance")
                .param("start", "2024-01-01")
                .param("end", "2024-01-31"))
                .andExpect(status().isOk());
    }

    @Test
    void generateReport_Success() throws Exception {
        RevenueReport report = new RevenueReport();
        report.setTotalRevenue(BigDecimal.ONE);

        when(analyticsService.generateMockRevenueReport()).thenReturn(report);

        mockMvc.perform(post("/api/analytics/revenue/generate-mock"))
                .andExpect(status().isOk());
    }
}
