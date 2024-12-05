CREATE DATABASE IF NOT EXISTS calidad2024;

USE calidad2024;

CREATE TABLE IF NOT EXISTS usuariosFinal (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(15),
    email VARCHAR(50),
    password VARCHAR(16),
    isLogged TINYINT(1)
);