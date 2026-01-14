-- Create Databases for GestorGYM Microservices

CREATE DATABASE IF NOT EXISTS gym_users;
CREATE DATABASE IF NOT EXISTS gym_access;
CREATE DATABASE IF NOT EXISTS gym_payments;
CREATE DATABASE IF NOT EXISTS gym_bookings;
CREATE DATABASE IF NOT EXISTS gym_notifications;
CREATE DATABASE IF NOT EXISTS gym_analytics;
CREATE DATABASE IF NOT EXISTS gym_chatbot;
CREATE DATABASE IF NOT EXISTS gym_vision;
CREATE DATABASE IF NOT EXISTS gym_workouts;
CREATE DATABASE IF NOT EXISTS gym_nutrition;
CREATE DATABASE IF NOT EXISTS gym_recommendations;
CREATE DATABASE IF NOT EXISTS gym_feedback;
CREATE DATABASE IF NOT EXISTS gym_schedule;
CREATE DATABASE IF NOT EXISTS gym_shared;

-- Grant permissions (optional for development, but good practice)
-- GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
-- FLUSH PRIVILEGES;
