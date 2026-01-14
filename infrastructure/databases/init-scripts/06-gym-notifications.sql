-- ============================================
-- Schema para Notification Service
-- Base de datos: gym_notifications
-- ============================================

USE gym_notifications;

-- ============================================
-- 1. Plantillas de Notificaciones
-- ============================================
CREATE TABLE IF NOT EXISTS notification_templates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    type ENUM('email', 'push', 'sms') NOT NULL,
    subject VARCHAR(255),
    body TEXT NOT NULL COMMENT 'Soporta placeholders {{name}}',
    language VARCHAR(5) DEFAULT 'es',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar plantillas base
INSERT INTO notification_templates (name, type, subject, body, language) VALUES
('welcome_email', 'email', '¡Bienvenido a GestorGYM!', 'Hola {{member_name}}, gracias por unirte...', 'es'),
('payment_success', 'email', 'Pago Recibido', 'Hemos recibido tu pago de ${{amount}}...', 'es'),
('booking_confirmed', 'push', NULL, 'Tu clase de {{class_name}} está confirmada para {{time}}', 'es');


-- ============================================
-- 2. Preferencias de Usuario
-- ============================================
CREATE TABLE IF NOT EXISTS user_notification_preferences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL UNIQUE,
    push_enabled BOOLEAN DEFAULT TRUE,
    email_enabled BOOLEAN DEFAULT TRUE,
    sms_enabled BOOLEAN DEFAULT False,
    marketing_enabled BOOLEAN DEFAULT FALSE,
    reminder_enabled BOOLEAN DEFAULT TRUE,
    quiet_hours_start TIME,
    quiet_hours_end TIME,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================
-- 3. Log de Notificaciones
-- ============================================
CREATE TABLE IF NOT EXISTS notification_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    template_id BIGINT,
    channel ENUM('email', 'push', 'sms') NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    content TEXT,
    status ENUM('sent', 'failed', 'opened', 'clicked') DEFAULT 'sent',
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    error_message TEXT,
    
    INDEX idx_member_logs (member_id),
    INDEX idx_sent_at (sent_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
