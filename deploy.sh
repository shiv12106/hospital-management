#!/bin/bash

# Hospital Management System Deployment Script

echo "🏥 Hospital Management System Deployment"
echo "========================================"

# Check if JAR file exists
if [ ! -f "target/hospital-0.0.1-SNAPSHOT.jar" ]; then
    echo "❌ JAR file not found. Building the application..."
    ./mvnw.cmd clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo "❌ Build failed. Exiting..."
        exit 1
    fi
fi

echo "✅ JAR file found: target/hospital-0.0.1-SNAPSHOT.jar"

# Deployment options
echo ""
echo "Choose deployment option:"
echo "1) Local JAR deployment (development)"
echo "2) Local JAR deployment with production profile"
echo "3) Docker deployment"
echo "4) Docker Compose deployment (recommended)"
echo "5) Exit"

read -p "Enter your choice (1-5): " choice

case $choice in
    1)
        echo "🚀 Starting local development deployment..."
        java -jar target/hospital-0.0.1-SNAPSHOT.jar
        ;;
    2)
        echo "🚀 Starting local production deployment..."
        java -jar target/hospital-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
        ;;
    3)
        echo "🐳 Building Docker image..."
        docker build -t hospital-management .
        if [ $? -eq 0 ]; then
            echo "✅ Docker image built successfully"
            echo "🚀 Starting Docker container..."
            docker run -p 8080:8080 --name hospital-app hospital-management
        else
            echo "❌ Docker build failed"
            exit 1
        fi
        ;;
    4)
        echo "🐳 Starting Docker Compose deployment..."
        if command -v docker-compose &> /dev/null; then
            docker-compose up -d
            echo "✅ Docker Compose deployment started"
            echo "🌐 Application will be available at: http://localhost:8080"
            echo "🗄️  Database will be available at: localhost:3306"
        else
            echo "❌ Docker Compose not found. Please install Docker Compose."
            exit 1
        fi
        ;;
    5)
        echo "👋 Exiting..."
        exit 0
        ;;
    *)
        echo "❌ Invalid choice. Exiting..."
        exit 1
        ;;
esac
