# Hospital Management System - Deployment Guide

## 🚀 Deployment Options

### Option 1: JAR File Deployment (Recommended for Production)

#### Step 1: Build the Application
```bash
# Clean and compile the project
.\mvnw.cmd clean compile

# Package the application as an executable JAR
.\mvnw.cmd clean package -DskipTests
```

#### Step 2: Production Configuration
Create `application-prod.properties`:

```properties
# Production Configuration
spring.application.name=hospital

# Server Configuration
server.port=8080

# Production Database (MySQL/PostgreSQL recommended)
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false

# H2 Console (Disable in production)
spring.h2.console.enabled=false

# Security Configuration
spring.security.user.name=admin
spring.security.user.password=your_secure_password

# Logging Configuration
logging.level.root=WARN
logging.level.com.hms.hospital=INFO
```

#### Step 3: Add Production Database Dependency
Update `pom.xml` to include MySQL/PostgreSQL:

```xml
<!-- For MySQL -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- For PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

#### Step 4: Deploy and Run
```bash
# Run with production profile
java -jar target/hospital-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# Or with custom configuration file
java -jar target/hospital-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/application-prod.properties
```

---

### Option 2: Docker Deployment

#### Step 1: Create Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/hospital-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Step 2: Build Docker Image
```bash
# First build the JAR
.\mvnw.cmd clean package -DskipTests

# Build Docker image
docker build -t hospital-management .

# Run container
docker run -p 8080:8080 hospital-management
```

#### Step 3: Docker Compose (Recommended)
Create `docker-compose.yml`:

```yaml
version: '3.8'

services:
  hospital-app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/hospital_db
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=rootpassword
    depends_on:
      - db
    restart: unless-stopped

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: hospital_db
    volumes:
      - mysql_data:/var/lib/mysql
    restart: unless-stopped

volumes:
  mysql_data:
```

```bash
# Deploy with Docker Compose
docker-compose up -d
```

---

### Option 3: Cloud Deployment

#### AWS Elastic Beanstalk
1. Build the JAR file: `.\mvnw.cmd clean package -DskipTests`
2. Upload `target/hospital-0.0.1-SNAPSHOT.jar` to Elastic Beanstalk
3. Configure environment variables for database connection

#### Heroku
1. Create `Procfile`:
   ```
   web: java -jar target/hospital-0.0.1-SNAPSHOT.jar --server.port=$PORT
   ```
2. Deploy:
   ```bash
   heroku create your-app-name
   git add .
   git commit -m "Deploy to Heroku"
   git push heroku main
   ```

#### Google Cloud Platform
1. Build JAR file
2. Use Cloud Run or App Engine
3. Configure Cloud SQL for database

---

## 🔧 Pre-Deployment Checklist

### ✅ Configuration Updates
- [ ] Update database configuration for production
- [ ] Disable H2 console in production
- [ ] Set appropriate logging levels
- [ ] Configure security settings
- [ ] Set production port (80/443 with reverse proxy)

### ✅ Database Setup
- [ ] Create production database
- [ ] Run database migrations if needed
- [ ] Set up database backups
- [ ] Configure connection pooling

### ✅ Security
- [ ] Change default passwords
- [ ] Enable HTTPS/SSL
- [ ] Configure firewall rules
- [ ] Set up authentication and authorization
- [ ] Enable security headers

### ✅ Performance
- [ ] Configure connection pooling
- [ ] Enable caching
- [ ] Set up monitoring
- [ ] Configure load balancing if needed

---

## 🌐 Access After Deployment

### Default Credentials
- **Admin**: `admin / admin123`
- **Doctor**: `doctor / doctor123`
- **Patient**: `patient / patient123`

### URLs
- **Application**: `http://localhost:8080` (or your domain)
- **H2 Console** (dev only): `http://localhost:8082/h2-console`

---

## 📊 Monitoring and Maintenance

### Health Checks
```bash
# Application health
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info
```

### Logs
```bash
# View application logs
docker logs hospital-app

# Or for JAR deployment
tail -f application.log
```

---

## 🚨 Troubleshooting

### Common Issues
1. **Port conflicts**: Change server port in application.properties
2. **Database connection**: Verify database URL, credentials, and network access
3. **Memory issues**: Increase JVM heap size: `-Xmx2g`
4. **File permissions**: Ensure proper read/write permissions

### Debug Mode
```bash
# Run with debug logging
java -jar target/hospital-0.0.1-SNAPSHOT.jar --logging.level.com.hms.hospital=DEBUG
```

---

## 📞 Support

For deployment issues:
1. Check application logs
2. Verify database connectivity
3. Ensure all dependencies are available
4. Check system resources (memory, disk space)

---

**🎉 Your Hospital Management System is now ready for deployment!**
