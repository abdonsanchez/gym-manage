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

/**
 * Servicio de lógica de negocio para pagos y facturación.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final InvoiceRepository invoiceRepository;

    /**
     * Recupera todas las facturas de un miembro.
     *
     * @param memberId ID del miembro.
     * @return Lista de facturas.
     */
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByMember(Long memberId) {
        return invoiceRepository.findByMemberId(memberId);
    }

    /**
     * Crea una nueva factura.
     * Genera automáticamente un número de factura y fecha de vencimiento si no se
     * proporcionan.
     *
     * @param invoice Datos de la factura.
     * @return Factura guardada.
     */
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

    /**
     * Procesa el pago de una factura, cambiando su estado a 'paid'.
     *
     * @param invoiceId ID de la factura.
     * @return Factura pagada.
     * @throws EntityNotFoundException Si la factura no existe.
     */
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
