package org.eduplay.eduplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        if (url.startsWith("postgres://") || url.startsWith("postgresql://")) {
            return normalizePostgresUriToJdbc(url);
        }
        return null;
    }

    private static String normalizePostgresUriToJdbc(String rawUrl) {
        try {
            URI uri = URI.create(rawUrl);
            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 5432 : uri.getPort();
            String path = uri.getPath() == null ? "" : uri.getPath();
            String database = path.startsWith("/") ? path.substring(1) : path;
            if (database.isBlank()) {
                return null;
            }

            StringBuilder jdbc = new StringBuilder("jdbc:postgresql://")
                    .append(host)
                    .append(":")
                    .append(port)
                    .append("/")
                    .append(database);

            String query = uri.getQuery();
            String userInfo = uri.getUserInfo();
            if (userInfo != null && !userInfo.isBlank()) {
                String[] parts = userInfo.split(":", 2);
                String user = parts.length > 0 ? parts[0] : "";
                String pass = parts.length > 1 ? parts[1] : "";
                String creds = "user=" + encode(user) + "&password=" + encode(pass);
                query = (query == null || query.isBlank()) ? creds : query + "&" + creds;
            }

            if (query != null && !query.isBlank()) {
                jdbc.append("?").append(query);
            }
            return jdbc.toString();
        } catch (Exception ignored) {
            return null;
        }
    }

    private static String encode(String value) {
        return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
    }
}
