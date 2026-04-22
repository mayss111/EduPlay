# Production Deployment Configuration
# Optimized settings for Render hosting and Vercel frontend

## Backend (Render) Optimization

### Spring Boot Settings
```properties
# Application Performance
spring.application.name=EduPlay
server.port=8080
server.servlet.context-path=/api

# Database Connection Pooling (HikariCP)
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# JPA/Hibernate Optimization
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# Actuator for Health Checks
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
management.metrics.tags.application=${spring.application.name}

# Logging
logging.level.root=INFO
logging.level.org.eduplay.eduplay=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n
```

### Ollama Integration Optimization
```properties
# Timeouts adjusted for Render's unpredictable latency
ollama.timeout.connect=10000  # 10s (was 8s)
ollama.timeout.read=20000     # 20s (was 15s)
ollama.cache.ttl=90           # 90s per profile
ollama.fallback.enabled=true
```

### Render Environment Variables
```bash
# Add to Render dashboard:
JAVA_OPTS=-Xmx512M -Xms256M -XX:+UseG1GC
PORT=8080
RENDER_EXTERNAL_URL=https://eduplay-g3as.onrender.com
```

## Frontend (Vercel) Optimization

### vercel.json Configuration
```json
{
  "buildCommand": "ng build --configuration production",
  "outputDirectory": "dist/eduplay-frontend",
  "rewrites": [
    {
      "source": "/api/(.*)",
      "destination": "https://eduplay-g3as.onrender.com/api/$1"
    }
  ],
  "headers": [
    {
      "source": "/(.*)",
      "headers": [
        {
          "key": "Cache-Control",
          "value": "public, max-age=31536000, immutable"
        }
      ]
    },
    {
      "source": "/api/(.*)",
      "headers": [
        {
          "key": "Cache-Control",
          "value": "no-cache, no-store, must-revalidate"
        }
      ]
    }
  ]
}
```

### Build Performance
- Enable Angular build cache: `ng build --cache`
- Use ng serve with --poll for dev: `ng serve --poll 2000`
- Tree-shaking and minification enabled in production build

## Deployment Checklist

### Pre-Deployment
- [ ] All unit tests pass: `mvnw clean test`
- [ ] Backend compilation: `mvnw clean compile`
- [ ] Quality diagnostic: `bash scripts/test-quality.sh https://eduplay-g3as.onrender.com 95`
- [ ] Frontend build: `ng build --configuration production`
- [ ] No console errors in browser dev tools
- [ ] Git commits all pushed to main branch

### Post-Deployment Verification
- [ ] Backend health check: `curl https://eduplay-g3as.onrender.com/api/actuator/health`
- [ ] Frontend loads at https://edu-play-nu.vercel.app
- [ ] Login flow works (STUDENT role only)
- [ ] Question generation works for all subjects
- [ ] XP updates visible in dashboard after game
- [ ] No error logs in browser console
- [ ] API timeouts handled gracefully with fallback

### Performance Monitoring
- Monitor Render response times: < 2s for /questions endpoint
- Monitor Vercel frontend load time: < 3s
- Check Ollama availability: `curl http://localhost:11434/api/models` (dev only)
- Review Render logs for timeout errors

## Scaling Recommendations

### If questions timeout frequently:
1. Increase Ollama timeout in application.properties
2. Increase fallback question pool size
3. Pre-generate and cache common question sets
4. Consider upgrading Render plan for better CPU allocation

### If database performance degrades:
1. Add database indices on: userId, subject, difficulty, classLevel
2. Archive old scores/results periodically
3. Implement read replicas for user queries

### If frontend load time increases:
1. Enable Vercel Analytics to identify bottlenecks
2. Implement code splitting for feature modules
3. Add service worker for offline capability
4. Optimize image sizes and lazy-load components
