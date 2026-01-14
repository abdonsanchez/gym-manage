-- ============================================
-- Schema para Booking Service
-- Base de datos: gym_bookings
-- ============================================

USE gym_bookings;

-- ============================================
-- 1. Entrenadores
-- ============================================
CREATE TABLE IF NOT EXISTS trainers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    specialties JSON COMMENT 'Array de especialidades',
    bio TEXT,
    photo_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2. Clases Grupales (Definición)
-- ============================================
CREATE TABLE IF NOT EXISTS classes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL COMMENT 'yoga, spinning, hiit, etc',
    difficulty_level ENUM('beginner', 'intermediate', 'advanced') DEFAULT 'beginner',
    duration_minutes INT NOT NULL,
    capacity INT NOT NULL,
    instructor_id BIGINT,
    room_id BIGINT COMMENT 'Referencia a una instalación',
    is_active BOOLEAN DEFAULT TRUE,
    
    CONSTRAINT fk_classes_instructor FOREIGN KEY (instructor_id) REFERENCES trainers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================
-- 3. Horarios de Clases (Scheduling)
-- ============================================
CREATE TABLE IF NOT EXISTS class_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_id BIGINT NOT NULL,
    day_of_week INT NOT NULL COMMENT '0=Sunday, 6=Saturday',
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_recurring BOOLEAN DEFAULT TRUE,
    
    CONSTRAINT fk_schedules_class FOREIGN KEY (class_id) REFERENCES classes(id),
    INDEX idx_day_time (day_of_week, start_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. Reservas de Clases
-- ============================================
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL COMMENT 'Referencia a gym_users',
    class_schedule_id BIGINT NOT NULL,
    booking_date DATE NOT NULL COMMENT 'Fecha específica de la clase',
    status ENUM('confirmed', 'cancelled', 'no_show', 'attended') DEFAULT 'confirmed',
    cancelled_at TIMESTAMP,
    cancellation_reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_bookings_schedule FOREIGN KEY (class_schedule_id) REFERENCES class_schedules(id),
    INDEX idx_member_bookings (member_id),
    INDEX idx_schedule_date (class_schedule_id, booking_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 5. Disponibilidad de Entrenadores
-- ============================================
CREATE TABLE IF NOT EXISTS trainer_availability (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trainer_id BIGINT NOT NULL,
    day_of_week INT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    
    CONSTRAINT fk_availability_trainer FOREIGN KEY (trainer_id) REFERENCES trainers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
