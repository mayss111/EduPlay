package org.eduplay.eduplay.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseBootstrap {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ApplicationRunner ensureUsersTable() {
        return args -> {
            if (!isPostgres()) {
                return;
            }

            jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS public.app_users (
                    id BIGSERIAL PRIMARY KEY,
                    first_name VARCHAR(255) NOT NULL,
                    username VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    role VARCHAR(50) NOT NULL,
                    app_language VARCHAR(50),
                    class_level INTEGER,
                    avatar_index INTEGER,
                    total_xp INTEGER DEFAULT 0,
                    streak INTEGER DEFAULT 0,
                    created_at TIMESTAMP
                )
                """);

            jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_app_users_username ON public.app_users(username)");
            log.info("Database bootstrap check completed for public.app_users");
        };
    }

    private boolean isPostgres() {
        try (Connection connection = dataSource.getConnection()) {
            String product = connection.getMetaData().getDatabaseProductName();
            return product != null && product.toLowerCase().contains("postgres");
        } catch (Exception e) {
            log.warn("Unable to detect database product during bootstrap: {}", e.getMessage());
            return false;
        }
    }
}
