-- ============================================
-- Schema para User Service
-- Base de datos: gym_users
-- ============================================

USE gym_users;

-- ============================================
-- 1. Tabla de Tipos de Membresía
-- ============================================
CREATE TABLE IF NOT EXISTS membership_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE COMMENT 'Nombre: Básico, Premium, VIP',
    description TEXT,
    price_monthly DECIMAL(10, 2) NOT NULL,
    price_annual DECIMAL(10, 2) NOT NULL,
    features JSON COMMENT 'Lista de características incluidas',
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar datos semilla básicos
INSERT INTO membership_types (name, description, price_monthly, price_annual, features, active) VALUES 
('Basic', 'Acceso básico al gimnasio', 29.99, 299.99, '["access_gym", "lockers"]', TRUE),
('Premium', 'Acceso total + clases grupales', 49.99, 499.99, '["access_gym", "lockers", "group_classes", "sauna"]', TRUE),
('VIP', 'Todo incluido + entrenador personal', 89.99, 899.99, '["access_gym", "lockers", "group_classes", "sauna", "personal_trainer"]', TRUE);


-- ============================================
-- 2. Tabla de Miembros
-- ============================================
CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único del miembro',
    document_id VARCHAR(50) UNIQUE NOT NULL COMMENT 'Documento de identidad (cédula, DNI, pasaporte)',
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    date_of_birth DATE NOT NULL,
    gender ENUM('M', 'F', 'Other', 'PreferNotToSay') NOT NULL,
    address TEXT,
    profile_photo_url VARCHAR(500) COMMENT 'URL de la foto de perfil en MinIO',
    membership_type_id INT NOT NULL,
    membership_start_date DATE NOT NULL,
    membership_end_date DATE NOT NULL,
    status ENUM('active', 'suspended', 'cancelled') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_members_membership_type FOREIGN KEY (membership_type_id) REFERENCES membership_types(id),
    INDEX idx_email (email),
    INDEX idx_document_id (document_id),
    INDEX idx_membership_status (membership_type_id, status),
    INDEX idx_membership_end_date (membership_end_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================
-- 3. Tabla de Historial Médico
-- ============================================
CREATE TABLE IF NOT EXISTS medical_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL UNIQUE,
    has_heart_condition BOOLEAN DEFAULT FALSE,
    has_chest_pain BOOLEAN DEFAULT FALSE,
    has_balance_issues BOOLEAN DEFAULT FALSE,
    current_medications TEXT,
    allergies TEXT,
    injuries_history TEXT,
    medical_restrictions TEXT,
    staff_notes TEXT,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_medical_member FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================
-- 4. Tabla de Contactos de Emergencia
-- ============================================
CREATE TABLE IF NOT EXISTS emergency_contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    relationship VARCHAR(50),
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_emergency_member FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
