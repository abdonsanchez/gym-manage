package com.gym.payment.service;

import com.gym.payment.model.Invoice;
import com.gym.payment.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setAmount(new BigDecimal("100.00"));
        invoice.setStatus(Invoice.Status.pending);
    }

    @Test
    void createInvoice_GeneratesDetails() {
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Invoice created = paymentService.createInvoice(new Invoice());

        assertNotNull(created.getInvoiceNumber());
        assertNotNull(created.getDueDate());
    }

    @Test
    void payInvoice_Success() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        Invoice paid = paymentService.payInvoice(1L);

        assertEquals(Invoice.Status.paid, paid.getStatus());
    }

    @Test
    void payInvoice_NotFound_ThrowsException() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> paymentService.payInvoice(1L));
    }
}
