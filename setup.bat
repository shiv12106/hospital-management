@echo off
echo ===============================================
echo Hospital Management System - Setup & Run
echo ===============================================
echo.

:: Check if Java is installed
echo [1/4] Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher from: https://adoptium.net/
    echo After installation, run this script again.
    pause
    exit /b 1
)

:: Check Java version
for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr "version"') do set "java_version=%%i"
set java_version=%java_version:"=%
echo Java version detected: %java_version%

:: Extract major version
for /f "tokens=1,2 delims=." %%a in ("%java_version%") do (
    if "%%a"=="1" (
        set major_version=%%b
    ) else (
        set major_version=%%a
    )
)

if %major_version% lss 17 (
    echo ERROR: Java 17 or higher is required. Current version: %major_version%
    echo Please install Java 17+ from: https://adoptium.net/
    pause
    exit /b 1
)

echo Java 17+ verified successfully!

:: Check if Maven wrapper exists
echo.
echo [2/4] Checking project files...
if not exist "mvnw.cmd" (
    echo ERROR: Maven wrapper not found!
    echo Make sure you're in the correct project directory.
    pause
    exit /b 1
)

echo Project files verified!

:: Build the project
echo.
echo [3/4] Building the project...
call mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo Build successful!

:: Run the application
echo.
echo [4/4] Starting Hospital Management System...
echo ===============================================
echo Application will start on: http://localhost:8081
echo Database Console: http://localhost:8081/h2-console
echo ===============================================
echo.
echo Press Ctrl+C to stop the server
echo.

call mvnw.cmd spring-boot:run
