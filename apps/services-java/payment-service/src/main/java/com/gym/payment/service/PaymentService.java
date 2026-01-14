package com.gym.payment.service;

import com.gym.payment.model.Invoice;
import com.gym.payment.repository.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByMember(Long memberId) {
        return invoiceRepository.findByMemberId(memberId);
    }

    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        if (invoice.getInvoiceNumber() == null) {
            invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (invoice.getDueDate() == null) {
            invoice.setDueDate(LocalDate.now().plusDays(30));
        }
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice payInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        // Mock payment processing logic
        invoice.setStatus(Invoice.Status.paid);

        // TODO: Send event to Kafka (payment.success) to unlock Member access if
        // blocked

        return invoiceRepository.save(invoice);
    }
}
