@echo off
REM Hospital Management System - Railway Deployment Script for Windows

echo 🏥 Hospital Management System - Railway Deployment
echo ==================================================

REM Check if Railway CLI is installed
where railway >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Railway CLI not found. Installing...
    npm install -g @railway/cli
    if %errorlevel% neq 0 (
        echo ❌ Failed to install Railway CLI. Please install Node.js first.
        pause
        exit /b 1
    )
)

echo ✅ Railway CLI found

REM Check if logged in
railway whoami >nul 2>nul
if %errorlevel% neq 0 (
    echo 🔐 Please login to Railway:
    railway login
)

REM Initialize Railway project
if not exist "railway.json" (
    echo 🚀 Initializing Railway project...
    railway init
)

REM Build the application
echo 🔨 Building the application...
call .\mvnw.cmd clean package -DskipTests

if %errorlevel% neq 0 (
    echo ❌ Build failed. Exiting...
    pause
    exit /b 1
)

echo ✅ Build successful

REM Deploy to Railway
echo 🚀 Deploying to Railway...
railway up

if %errorlevel% equ 0 (
    echo ✅ Deployment successful!
    echo.
    echo 🌐 Your application is now live on Railway!
    echo 📋 Next steps:
    echo    1. Visit Railway dashboard to get your app URL
    echo    2. Configure PostgreSQL database in Railway
    echo    3. Set environment variables for database connection
    echo    4. Test your application
) else (
    echo ❌ Deployment failed. Please check the logs.
    pause
    exit /b 1
)

pause
