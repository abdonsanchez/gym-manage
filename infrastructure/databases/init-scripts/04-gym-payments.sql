-- ============================================
-- Schema para Payment Service
-- Base de datos: gym_payments
-- ============================================

USE gym_payments;

-- ============================================
-- 1. Tabla de Facturas
-- ============================================
CREATE TABLE IF NOT EXISTS invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL COMMENT 'Referencia a gym_users.members.id',
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    tax DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    total DECIMAL(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    status ENUM('pending', 'paid', 'overdue', 'cancelled') DEFAULT 'pending',
    pdf_url VARCHAR(500) COMMENT 'URL en MinIO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_member_id (member_id),
    INDEX idx_status (status),
    INDEX idx_due_date (due_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2. Métodos de Pago
-- ============================================
CREATE TABLE IF NOT EXISTS payment_methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    type ENUM('credit_card', 'debit_card', 'bank_transfer', 'cash') NOT NULL,
    card_last_four VARCHAR(4),
    card_token VARCHAR(255) COMMENT 'Tokenizado para PCI-DSS',
    expiry_date DATE,
    is_default BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_member (member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================
-- 3. Transacciones
-- ============================================
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    payment_method_id BIGINT,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_gateway_ref VARCHAR(100) COMMENT 'ID de transacción de Stripe/PayPal',
    status ENUM('success', 'failed', 'refunded', 'pending') DEFAULT 'pending',
    notes TEXT,
    
    CONSTRAINT fk_transactions_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(id),
    CONSTRAINT fk_transactions_method FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
    INDEX idx_gateway_ref (payment_gateway_ref)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. Pagos Recurrentes
-- ============================================
CREATE TABLE IF NOT EXISTS recurring_payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    frequency ENUM('monthly', 'annual') NOT NULL,
    next_charge_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_recurring_method FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
    INDEX idx_next_charge (next_charge_date, is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
