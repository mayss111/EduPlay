-- Script to create database and user for EduPlay
-- Edit the password before running or pass it interactively

CREATE DATABASE IF NOT EXISTS `eduplay_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Recommended: create a dedicated user (replace 'StrongPassword123!' with a secure password)
CREATE USER IF NOT EXISTS 'eduplay'@'localhost' IDENTIFIED BY 'StrongPassword123!';
CREATE USER IF NOT EXISTS 'eduplay'@'%' IDENTIFIED BY 'StrongPassword123!';

GRANT ALL PRIVILEGES ON `eduplay_db`.* TO 'eduplay'@'localhost';
GRANT ALL PRIVILEGES ON `eduplay_db`.* TO 'eduplay'@'%';
FLUSH PRIVILEGES;

-- Verify: select schema
USE `eduplay_db`;
SHOW TABLES;
