package com.gym.payment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    @NotNull
    private Long memberId;

    @Column(name = "invoice_number", unique = true, nullable = false)
    @NotBlank
    private String invoiceNumber;

    @Column(nullable = false)
    @NotNull
    private BigDecimal amount;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;

    @Column(nullable = false)
    @NotNull
    private BigDecimal total;

    @Column(name = "due_date", nullable = false)
    @NotNull
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.pending;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Status {
        pending, paid, overdue, cancelled
    }
}
