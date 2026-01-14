package com.gym.analytics.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "revenue_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "total_transactions")
    private Integer totalTransactions;

    @Column(name = "average_transaction")
    private BigDecimal averageTransaction;

    @Column(name = "payment_method_breakdown", columnDefinition = "json")
    private String paymentMethodBreakdown;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @PrePersist
    protected void onCreate() {
        generatedAt = LocalDateTime.now();
    }
}
