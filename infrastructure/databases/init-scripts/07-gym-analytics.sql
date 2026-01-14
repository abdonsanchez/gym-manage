-- ============================================
-- Schema para Analytics Service
-- Base de datos: gym_analytics
-- ============================================

USE gym_analytics;

-- ============================================
-- 1. Métricas de Asistencia Agregadas
-- ============================================
CREATE TABLE IF NOT EXISTS attendance_metrics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    hour INT NOT NULL,
    day_of_week INT NOT NULL,
    total_checkins INT DEFAULT 0,
    unique_members INT DEFAULT 0,
    average_stay_minutes INT DEFAULT 0,
    location VARCHAR(100) DEFAULT 'Main Branch',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_date_hour (date, hour)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================
-- 2. Reportes de Ingresos
-- ============================================
CREATE TABLE IF NOT EXISTS revenue_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    total_revenue DECIMAL(15, 2) DEFAULT 0.00,
    total_transactions INT DEFAULT 0,
    average_transaction DECIMAL(10, 2) DEFAULT 0.00,
    payment_method_breakdown JSON COMMENT 'Desglose por método de pago',
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 3. Estadísticas de Retención
-- ============================================
CREATE TABLE IF NOT EXISTS retention_stats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    month DATE NOT NULL COMMENT 'Primer día del mes analizado',
    total_members_start INT DEFAULT 0,
    new_members INT DEFAULT 0,
    churned_members INT DEFAULT 0,
    retention_rate DECIMAL(5, 2),
    churn_rate DECIMAL(5, 2),
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. Predicciones de Churn (Escritas por AI Service)
-- ============================================
CREATE TABLE IF NOT EXISTS churn_predictions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    prediction_date DATE NOT NULL,
    churn_probability DECIMAL(5, 4) COMMENT '0.0000 to 1.0000',
    risk_level ENUM('low', 'medium', 'high') DEFAULT 'low',
    contributing_factors JSON,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_member (member_id),
    INDEX idx_risk (risk_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
