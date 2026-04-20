package org.eduplay.eduplay;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:eduplay_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.cache.type=none",
    "spring.session.store-type=none",
    "app.jwt.secret=TestJwtSecretKey_ChangeMe_2026_Min32Chars",
    "app.jwt.expiration=86400000",
    "app.cors.allowed-origins=http://localhost:4200"
})
class EduPlayApplicationTests {

    @Test
    void contextLoads() {
    }

}
