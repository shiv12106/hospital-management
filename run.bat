@echo off
echo ===============================================
echo Hospital Management System - Quick Start
echo ===============================================
echo.

:: Quick run without checks (assumes setup was done)
echo Starting Hospital Management System...
echo Application will be available at: http://localhost:8081
echo Press Ctrl+C to stop the server
echo.

call mvnw.cmd spring-boot:run
