-- DDL pour EduPlay
-- Supprimer les tables existantes pour éviter les conflits de nommage
DROP TABLE IF EXISTS scores CASCADE;
DROP TABLE IF EXISTS user_question_history CASCADE;
DROP TABLE IF EXISTS question_bank CASCADE;
DROP TABLE IF EXISTS app_users CASCADE;

-- Table des utilisateurs
CREATE TABLE app_users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    app_language VARCHAR(50) DEFAULT 'FRENCH',
    class_level INTEGER DEFAULT 1,
    avatar_index INTEGER DEFAULT 0,
    total_xp INTEGER DEFAULT 0,
    streak INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table de la banque de questions
CREATE TABLE question_bank (
    id BIGSERIAL PRIMARY KEY,
    question_text TEXT NOT NULL,
    choice_a VARCHAR(255) NOT NULL,
    choice_b VARCHAR(255) NOT NULL,
    choice_c VARCHAR(255) NOT NULL,
    choice_d VARCHAR(255) NOT NULL,
    correct_choice VARCHAR(255) NOT NULL,
    explanation TEXT,
    subject VARCHAR(255) NOT NULL,
    class_level INTEGER NOT NULL,
    difficulty VARCHAR(255) NOT NULL,
    app_language VARCHAR(255) NOT NULL,
    topic_tag VARCHAR(255),
    quality_score INTEGER,
    usage_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des scores
CREATE TABLE scores (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES app_users(id),
    subject VARCHAR(255),
    points INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table de l'historique des questions
CREATE TABLE user_question_history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    is_correct BOOLEAN,
    answered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
