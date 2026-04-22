@echo off
REM Start backend
cd /d "%~dp0backend"
echo Starting Spring Boot backend...
call mvnw.cmd spring-boot:run

