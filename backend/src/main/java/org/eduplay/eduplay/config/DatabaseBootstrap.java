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
            log.info("Database bootstrap check completed (handled by schema.sql)");
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
