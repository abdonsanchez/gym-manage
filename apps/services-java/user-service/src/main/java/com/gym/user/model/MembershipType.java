package com.gym.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad JPA que representa los tipos de membresía disponibles.
 *
 * <p>Define los niveles de acceso y beneficios (Básico, Premium, VIP).</p>
 */
@Entity
@Table(name = "membership_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_monthly", nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal priceMonthly;

    @Column(name = "price_annual", nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal priceAnnual;

    /**
     * Lista de características en formato JSON.
     * Ejemplo: ["access_gym", "sauna", "classes"]
     */
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> features;

    @Builder.Default
    private Boolean active = true;

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
}
