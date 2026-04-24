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
            String normalized = toJdbcUrl(explicitDatasource);
            if (normalized != null) {
                System.setProperty("spring.datasource.url", normalized);
            }
            return;
        }

        String rawDatabaseUrl = System.getenv("DATABASE_URL");
        if (rawDatabaseUrl == null || rawDatabaseUrl.isBlank()) {
            return;
        }

        String normalized = toJdbcUrl(rawDatabaseUrl);
        if (normalized != null) {
            System.setProperty("spring.datasource.url", normalized);
        }
    }

    private static String toJdbcUrl(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }
        if (url.startsWith("jdbc:")) {
            return url;
        }
        if (url.startsWith("postgres://")) {
            return "jdbc:postgresql://" + url.substring("postgres://".length());
        }
        if (url.startsWith("postgresql://")) {
            return "jdbc:postgresql://" + url.substring("postgresql://".length());
        }
        return null;
    }
}
