package com.gym.payment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    @NotNull
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentType type;

    @Column(name = "card_last_four", length = 4)
    private String cardLastFour;

    @Column(name = "card_token")
    private String cardToken;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum PaymentType {
        credit_card, debit_card, bank_transfer, cash
    }
}
