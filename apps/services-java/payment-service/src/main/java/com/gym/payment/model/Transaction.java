package com.gym.payment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    @NotNull
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @NotNull
    private BigDecimal amount;

    @Column(name = "transaction_date", updatable = false)
    private LocalDateTime transactionDate;

    @Column(name = "payment_gateway_ref")
    private String paymentGatewayRef;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.pending;

    private String notes;

    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
    }

    public enum Status {
        success, failed, refunded, pending
    }
}
