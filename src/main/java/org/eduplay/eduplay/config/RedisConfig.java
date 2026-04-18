package org.eduplay.eduplay.config;



import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisConfig {
    // Spring Boot auto-configure Redis via application.properties
    // @EnableCaching active le cache sur toute l'application
}