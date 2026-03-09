@echo off
echo ===============================================
echo Hospital Management System - Dependency Check
echo ===============================================
echo.

echo [1/3] Checking Java Installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    echo 📥 Please download and install Java 17+ from: https://adoptium.net/
    echo.
    goto :end
)

:: Check Java version
for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr "version"') do set "java_version=%%i"
set java_version=%java_version:"=%
echo ✅ Java version: %java_version%

:: Extract major version
for /f "tokens=1,2 delims=." %%a in ("%java_version%") do (
    if "%%a"=="1" (
        set major_version=%%b
    ) else (
        set major_version=%%a
    )
)

if %major_version% lss 17 (
    echo ❌ Java 17 or higher is required. Current version: %major_version%
    echo 📥 Please upgrade Java from: https://adoptium.net/
    echo.
    goto :end
)

echo ✅ Java 17+ verified successfully!
echo.

echo [2/3] Checking Project Files...
if not exist "pom.xml" (
    echo ❌ pom.xml not found!
    echo 📁 Make sure you're in the correct project directory.
    echo.
    goto :end
)

if not exist "mvnw.cmd" (
    echo ❌ Maven wrapper not found!
    echo 📁 Project files may be corrupted.
    echo.
    goto :end
)

echo ✅ Project files verified!
echo.

echo [3/3] Checking Maven Wrapper...
call mvnw.cmd -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven wrapper not working!
    echo 🔄 Trying to fix...
    call mvnw.cmd clean >nul 2>&1
    if %errorlevel% neq 0 (
        echo ❌ Unable to fix Maven wrapper automatically.
        echo 📥 Please download Maven manually or check your network connection.
        echo.
        goto :end
    )
)

echo ✅ Maven wrapper working correctly!
echo.

echo ===============================================
echo ✅ ALL DEPENDENCIES CHECKED SUCCESSFULLY!
echo ===============================================
echo.
echo 🚀 You can now run the project using:
echo    run.bat
echo.
echo 📝 For setup instructions, see README.md
echo.

:end
pause
