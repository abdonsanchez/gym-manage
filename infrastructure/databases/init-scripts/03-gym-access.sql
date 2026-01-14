-- ============================================
-- Schema para Access Control Service
-- Base de datos: gym_access
-- ============================================

USE gym_access;

-- ============================================
-- 1. Logs de Acceso
-- ============================================
CREATE TABLE IF NOT EXISTS access_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL COMMENT 'Referencia a gym_users.members.id',
    access_method ENUM('qr', 'rfid', 'facial') NOT NULL,
    access_type ENUM('check_in', 'check_out') NOT NULL,
    location VARCHAR(100) DEFAULT 'Main Branch',
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    device_id VARCHAR(50),
    status ENUM('success', 'denied') NOT NULL,
    denial_reason VARCHAR(255),
    
    INDEX idx_member_timestamp (member_id, timestamp),
    INDEX idx_timestamp (timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2. Códigos QR Dinámicos
-- ============================================
CREATE TABLE IF NOT EXISTS qr_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    qr_data VARCHAR(255) UNIQUE NOT NULL,
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    
    INDEX idx_qr_data (qr_data),
    INDEX idx_member (member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 3. Tarjetas RFID
-- ============================================
CREATE TABLE IF NOT EXISTS rfid_cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    card_number VARCHAR(100) UNIQUE NOT NULL,
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    is_reported_lost BOOLEAN DEFAULT FALSE,
    
    INDEX idx_card_number (card_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. Perfiles de Reconocimiento Facial
-- ============================================
CREATE TABLE IF NOT EXISTS facial_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL UNIQUE,
    encoding_data LONGBLOB COMMENT 'Vector de características faciales',
    photo_reference_url VARCHAR(500) COMMENT 'Foto en MinIO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
