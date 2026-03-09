-- Hospital Management System Database Initialization Script for MySQL
-- This script will be automatically run when the MySQL container starts for the first time

-- Create additional database user if needed (MySQL creates the user from environment variables)
-- CREATE USER IF NOT EXISTS 'hospital_user'@'%' IDENTIFIED BY 'secure_password_change_me';
-- GRANT ALL PRIVILEGES ON hospital_db.* TO 'hospital_user'@'%';
-- FLUSH PRIVILEGES;

-- Set default character set
ALTER DATABASE hospital_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create sample data (optional - Spring Boot will create tables automatically)
-- You can add any initial data here if needed

-- Example: Insert default admin user (if you want to pre-populate some data)
-- INSERT INTO users (username, password, role, created_at) VALUES 
-- ('admin', '$2a$10$...', 'ROLE_ADMIN', NOW())
-- ON DUPLICATE KEY UPDATE username = VALUES(username);

-- Create indexes for better performance (Spring Boot JPA will create basic indexes)
-- CREATE INDEX idx_appointment_date ON appointments(appointment_date);
-- CREATE INDEX idx_patient_user ON patients(user_id);
-- CREATE INDEX idx_prescription_date ON prescriptions(prescribed_date);

-- Show database information
SELECT 'Hospital Management System Database Initialized' as message;
SHOW TABLES;
