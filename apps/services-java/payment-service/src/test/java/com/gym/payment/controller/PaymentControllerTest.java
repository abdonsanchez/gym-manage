package com.gym.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.payment.model.Invoice;
import com.gym.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createInvoice_Success() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setAmount(BigDecimal.TEN);

        when(paymentService.createInvoice(any(Invoice.class))).thenReturn(invoice);

        mockMvc.perform(post("/api/payments/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isOk());
    }

    @Test
    void getMemberInvoices_Success() throws Exception {
        when(paymentService.getInvoicesByMember(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/payments/invoices/member/1"))
                .andExpect(status().isOk());
    }
}
