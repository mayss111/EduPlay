package org.eduplay.eduplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EduPlayApplication {

    public static void main(String[] args) {
        normalizeRenderDatabaseUrl();
        SpringApplication.run(EduPlayApplication.class, args);
    }

    private static void normalizeRenderDatabaseUrl() {
        String explicitDatasource = System.getenv("SPRING_DATASOURCE_URL");
        if (explicitDatasource != null && !explicitDatasource.isBlank()) {
            return;
        }

        String rawDatabaseUrl = System.getenv("DATABASE_URL");
        if (rawDatabaseUrl == null || rawDatabaseUrl.isBlank()) {
            return;
        }

        if (rawDatabaseUrl.startsWith("postgres://")) {
            String jdbcUrl = "jdbc:postgresql://" + rawDatabaseUrl.substring("postgres://".length());
            System.setProperty("spring.datasource.url", jdbcUrl);
            return;
        }

        if (rawDatabaseUrl.startsWith("postgresql://")) {
            String jdbcUrl = "jdbc:postgresql://" + rawDatabaseUrl.substring("postgresql://".length());
            System.setProperty("spring.datasource.url", jdbcUrl);
            return;
        }

        if (rawDatabaseUrl.startsWith("jdbc:")) {
            System.setProperty("spring.datasource.url", rawDatabaseUrl);
        }
    }
}
