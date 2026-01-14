package com.gym.payment.controller;

import com.gym.payment.model.Invoice;
import com.gym.payment.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/invoices/member/{memberId}")
    public ResponseEntity<List<Invoice>> getMemberInvoices(@PathVariable Long memberId) {
        return ResponseEntity.ok(paymentService.getInvoicesByMember(memberId));
    }

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(paymentService.createInvoice(invoice));
    }

    @PostMapping("/invoices/{id}/pay")
    public ResponseEntity<Invoice> payInvoice(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.payInvoice(id));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
