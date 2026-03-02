CREATE DATABASE IF NOT EXISTS licodistribuciones;
USE licodistribuciones;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_sha256 CHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Usuario de ejemplo:
-- email: admin@licodistribuciones.com
-- contraseña original: Admin123*
INSERT INTO users (email, password_sha256)
VALUES ('admin@licodistribuciones.com', SHA2('Admin123*', 256))
ON DUPLICATE KEY UPDATE email = VALUES(email);
