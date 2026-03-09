# 🏥 Hospital Management System

A comprehensive Spring Boot + Thymeleaf hospital management application with role-based access control.

## 🚀 Quick Start

### Prerequisites
- **Java 17** or higher
- **Windows OS** (batch files optimized for Windows)

### Setup & Run (One-time setup)
```bash
# Double-click this file or run in command prompt
setup.bat
```

### Quick Run (Subsequent uses)
```bash
# Double-click this file or run in command prompt
run.bat
```

### Manual Run
```bash
cd d:\project_26\hospital
.\mvnw.cmd spring-boot:run
```

## 🌐 Access URLs

### Main Application
- **Home**: http://localhost:8082
- **Login**: http://localhost:8082/login

### User Roles & Access

#### 👤 Patients (ROLE_USER)
- **Dashboard**: http://localhost:8082/user/dashboard
- **My Prescriptions**: http://localhost:8082/user/prescriptions *(View Only)*
- **Appointments**: http://localhost:8082/user/appointments
- **Profile**: http://localhost:8082/user/profile

#### 👨‍⚕️ Doctors (ROLE_DOCTOR)
- **Dashboard**: http://localhost:8082/doctor/dashboard
- **Prescriptions Management**: http://localhost:8082/doctor/prescriptions *(Full CRUD)*
- **Patients**: http://localhost:8082/doctor/patients
- **Appointments**: http://localhost:8082/doctor/appointments
- **Reports**: http://localhost:8082/doctor/reports

#### 👨‍💼 Admins (ROLE_ADMIN)
- **Admin Dashboard**: http://localhost:8082/admin/dashboard

### Database Console
- **H2 Console**: http://localhost:8082/h2-console
- **JDBC URL**: `jdbc:h2:mem:hospitaldb`
- **Username**: `sa`
- **Password**: (leave blank)

## 📋 Features

### Core Functionality
- ✅ **User Authentication & Authorization**
- ✅ **Role-based Access Control** (Admin, Doctor, Patient)
- ✅ **Patient Management**
- ✅ **Doctor Management**
- ✅ **Appointment Scheduling**
- ✅ **Prescription Management** *(Doctor-only creation)*
- ✅ **Department Management**
- ✅ **Medical Reports**

### Recent Updates
- ✅ **Prescription Management Restructured**:
  - Patients can only view their prescriptions
  - Doctors have full prescription management capabilities
  - Moved "Add Prescription" form from patient dashboard to doctor dashboard

## 🏗️ Technology Stack

- **Backend**: Spring Boot 3.5.11
- **Frontend**: Thymeleaf + HTML5 + CSS3
- **Database**: H2 (In-memory)
- **Security**: Spring Security
- **Build Tool**: Maven
- **Java Version**: 17

## 📁 Project Structure

```
hospital/
├── src/main/java/com/hms/hospital/
│   ├── controller/          # REST Controllers
│   ├── entity/             # JPA Entities
│   ├── repository/         # Data Repositories
│   ├── service/           # Business Logic
│   └── config/            # Configuration Classes
├── src/main/resources/
│   ├── templates/         # Thymeleaf Templates
│   │   ├── admin/        # Admin pages
│   │   ├── doctor/       # Doctor pages
│   │   ├── user/         # Patient pages
│   │   └── ...
│   └── application.properties
├── setup.bat              # One-time setup script
├── run.bat                # Quick run script
└── pom.xml               # Maven configuration
```

## 🔧 Configuration

### Default Port
- **Application Port**: 8082
- **Change Port**: Edit `src/main/resources/application.properties`

### Database
- **Type**: H2 In-memory
- **Auto-creates tables on startup**
- **Data resets on application restart**

## 👥 Default Users (Created on Startup)

### Admin
- **Username**: admin
- **Password**: admin123
- **Role**: ADMIN

### Doctor
- **Username**: doctor
- **Password**: doctor123
- **Role**: DOCTOR

### Patient
- **Username**: patient
- **Password**: patient123
- **Role**: USER

## 🚨 Troubleshooting

### Port Already in Use
```bash
# Find process using port 8082
netstat -ano | findstr :8082

# Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

### Java Not Found
1. Download Java 17+ from: https://adoptium.net/
2. Install and restart command prompt
3. Run `setup.bat` again

### Build Issues
```bash
# Clean and rebuild
.\mvnw.cmd clean compile
```

## 📝 Presentation Notes for College

### Key Features to Demonstrate
1. **Role-based Access Control** - Different dashboards for different roles
2. **Prescription Management** - Doctor-only prescription creation
3. **Patient Data Privacy** - Patients can only view their own data
4. **Responsive UI** - Modern, clean interface
5. **Database Integration** - Real-time data persistence

### Security Features
- **Spring Security** integration
- **Role-based authorization**
- **Secure password hashing**
- **Session management**

### Technical Highlights
- **Spring Boot** rapid development
- **Thymeleaf** server-side templating
- **JPA/Hibernate** for database operations
- **Maven** dependency management
- **RESTful API** design

## 📞 Support

For issues or questions:
1. Check the troubleshooting section above
2. Verify Java 17+ is installed
3. Ensure port 8081 is available
4. Run `setup.bat` for first-time configuration

---

**Project Version**: 1.0  
**Last Updated**: March 2026  
**Compatible with**: Java 17+, Windows 10+
