@echo off
REM Hospital Management System Deployment Script for Windows

echo 🏥 Hospital Management System Deployment
echo ========================================

REM Check if JAR file exists
if not exist "target\hospital-0.0.1-SNAPSHOT.jar" (
    echo ❌ JAR file not found. Building the application...
    call .\mvnw.cmd clean package -DskipTests
    if errorlevel 1 (
        echo ❌ Build failed. Exiting...
        pause
        exit /b 1
    )
)

echo ✅ JAR file found: target\hospital-0.0.1-SNAPSHOT.jar

echo.
echo Choose deployment option:
echo 1) Local JAR deployment (development)
echo 2) Local JAR deployment with production profile
echo 3) Docker deployment
echo 4) Docker Compose deployment (recommended)
echo 5) Exit

set /p choice="Enter your choice (1-5): "

if "%choice%"=="1" (
    echo 🚀 Starting local development deployment...
    java -jar target\hospital-0.0.1-SNAPSHOT.jar
) else if "%choice%"=="2" (
    echo 🚀 Starting local production deployment...
    java -jar target\hospital-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
) else if "%choice%"=="3" (
    echo 🐳 Building Docker image...
    docker build -t hospital-management .
    if errorlevel 1 (
        echo ❌ Docker build failed
        pause
        exit /b 1
    )
    echo ✅ Docker image built successfully
    echo 🚀 Starting Docker container...
    docker run -p 8080:8080 --name hospital-app hospital-management
) else if "%choice%"=="4" (
    echo 🐳 Starting Docker Compose deployment...
    docker-compose up -d
    if errorlevel 1 (
        echo ❌ Docker Compose deployment failed
        pause
        exit /b 1
    )
    echo ✅ Docker Compose deployment started
    echo 🌐 Application will be available at: http://localhost:8080
    echo 🗄️  Database will be available at: localhost:3306
) else if "%choice%"=="5" (
    echo 👋 Exiting...
    exit /b 0
) else (
    echo ❌ Invalid choice. Exiting...
    pause
    exit /b 1
)

pause
