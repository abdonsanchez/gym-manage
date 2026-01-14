-- ============================================
-- Schemas para AI Services (Python)
-- ============================================

-- 1. CHATBOT AI
CREATE DATABASE IF NOT EXISTS gym_chatbot;
USE gym_chatbot;

CREATE TABLE IF NOT EXISTS conversations (
    id VARCHAR(36) PRIMARY KEY COMMENT 'UUID',
    member_id BIGINT COMMENT 'Nullable for anonymous',
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ended_at TIMESTAMP,
    channel VARCHAR(50) DEFAULT 'web',
    status ENUM('active', 'closed', 'escalated') DEFAULT 'active'
);

CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id VARCHAR(36) NOT NULL,
    role ENUM('user', 'assistant', 'system') NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id)
);

-- 2. VISION AI
CREATE DATABASE IF NOT EXISTS gym_vision;
USE gym_vision;

CREATE TABLE IF NOT EXISTS video_analyses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    exercise_name VARCHAR(100),
    video_url VARCHAR(255),
    status ENUM('processing', 'completed', 'failed') DEFAULT 'processing',
    score INT,
    feedback JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. WORKOUT PLANNER
CREATE DATABASE IF NOT EXISTS gym_workouts;
USE gym_workouts;

CREATE TABLE IF NOT EXISTS workout_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    goal VARCHAR(100),
    level VARCHAR(50),
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. NUTRITION AI
CREATE DATABASE IF NOT EXISTS gym_nutrition;
USE gym_nutrition;

CREATE TABLE IF NOT EXISTS meal_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    daily_calories INT,
    macros_split JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. RECOMMENDATION AI
CREATE DATABASE IF NOT EXISTS gym_recommendations;
USE gym_recommendations;

CREATE TABLE IF NOT EXISTS recommendations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    recommended_item_type VARCHAR(50) COMMENT 'class, product, trainer',
    recommended_item_id BIGINT,
    score DECIMAL(5, 4),
    reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
