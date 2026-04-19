# Dev setup (MySQL + Ollama)

This README explains how to run a local MySQL instance, start Ollama locally, and launch the Spring Boot backend connected to both.

Prerequisites
- Docker & Docker Compose (or Docker Desktop) installed
- Java 17
- Ollama installed locally
- Maven wrapper `./mvnw.cmd` is available in the project

1) Start MySQL with Docker Compose (recommended)

Open PowerShell in the `backend` folder and run:

```powershell
# Start MySQL container in background
docker-compose up -d

# Check container logs and status
docker-compose ps
docker-compose logs -f mysql
```

Defaults from docker-compose.yml:
- DB: `eduplay_db`
- User: `eduplay`
- Password: `StrongPassword123!`
- Root password: `rootpassword`
- Exposed port: 3306 (host)
- Exposed port: 3307 (host)

2) Option A: Use the provided SQL script to create DB/user manually (if you run MySQL yourself)

```powershell
# Run script using mysql client (replace root password when prompted)
mysql -u root -p < .\scripts\create_mysql_user.sql
```

3) Configure environment variables for the Spring Boot app (session only)

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:mysql://127.0.0.1:3307/eduplay_db?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true'
$env:SPRING_DATASOURCE_USERNAME = 'eduplay'
$env:SPRING_DATASOURCE_PASSWORD = 'StrongPassword123!'
```

4) Start Ollama locally

Open a separate terminal and run:

```powershell
ollama run llama3.2
```

If you want a different model, update `OLLAMA_MODEL` before starting the backend.

5) Build and run the backend

```powershell
# Build
.\mvnw.cmd -DskipTests package

# Run using the wrapper (it will pick up the env variables from the session)
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url=$env:SPRING_DATASOURCE_URL,--spring.datasource.username=$env:SPRING_DATASOURCE_USERNAME,--spring.datasource.password=$env:SPRING_DATASOURCE_PASSWORD"
```

Or run the packaged jar:

```powershell
java -jar -Dspring.profiles.active=prod -DSPRING_DATASOURCE_URL="$env:SPRING_DATASOURCE_URL" -DSPRING_DATASOURCE_USERNAME="$env:SPRING_DATASOURCE_USERNAME" -DSPRING_DATASOURCE_PASSWORD="$env:SPRING_DATASOURCE_PASSWORD" target\EduPlay-0.0.1-SNAPSHOT.jar
```

Troubleshooting
- If you see `Connection refused`, ensure the MySQL container is healthy and listening on port 3307 (`docker-compose ps`, `netstat -ano | findstr 3307`).
- If questions do not load, check that Ollama is running and that `ollama run llama3.2` is still active.
- If `Access denied`, check username/password and that the user is allowed to connect from `localhost` (or `%`).
- Use `docker-compose down -v` to remove container and volumes if you want a clean start.

Security note
- The passwords in the docker-compose are for local dev only. Do NOT commit production credentials.

