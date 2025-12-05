# Forum System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JWT](https://img.shields.io/badge/JWT-Authentication-blue.svg)](https://jwt.io/)
[![MariaDB](https://img.shields.io/badge/MariaDB-Database-blue.svg)](https://mariadb.org/)

A comprehensive forum application built with Spring Boot, featuring user authentication, hierarchical folder organization, post and comment management, and administrative controls. Developed as part of Telerik Academy A72 Web Development Program by Team 3.

## Table of Contents

- [What This Project Does](#what-this-project-does)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Security](#security)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Testing](#testing)

---

## What This Project Does

This is a full-featured forum application that allows users to:
- Register and log in with secure JWT authentication
- Create, read, update, and delete posts organized in folders
- Comment on posts and interact through likes
- Tag posts for easy discovery and filtering
- Browse content through a hierarchical folder structure
- Track post views and see trending content
- Manage users and content (admin features)

The application provides both a web interface (Thymeleaf) and a REST API for programmatic access.

---

## Key Features

- **User Management**: Registration, login/logout, user profiles, role-based access (User/Admin)
- **Authentication**: JWT-based authentication with HTTP-only cookies and BCrypt password hashing
- **Posts**: Create, edit, delete posts with titles, content, tags, and folder assignment
- **Comments**: Add comments to posts with edit/delete capabilities
- **Social Features**: Like/unlike posts and comments
- **Folder Organization**: Hierarchical folder structure with slug-based navigation
- **Tags**: Tag posts (up to 3 tags per post), filter by tags
- **View Tracking**: Unique daily view tracking per user to identify trending posts
- **Search & Filter**: Search posts by title, filter by tags, sort by date/likes/views
- **Admin Dashboard**: User management (block, delete, promote), content moderation
- **Soft Delete**: Non-destructive deletion with restore capability for posts, comments, and users
- **Pagination**: All lists paginated for performance

---

## Technology Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.5.7** - Application framework
- **Spring Data JPA** - Data persistence with Hibernate ORM
- **Spring Security** - Authentication and authorization
- **JWT (jjwt 0.12.3)** - Token-based authentication
- **MariaDB** - Relational database
- **Lombok** - Boilerplate code reduction
- **Gradle** - Build automation

### Frontend
- **Thymeleaf** - Server-side HTML templating
- **jQuery 3.7.1** - JavaScript library
- **Custom CSS** - Styling

### Testing
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework

---

## Architecture

The application follows a **layered architecture** pattern with clear separation of concerns:

**Presentation Layer**:
- MVC Controllers (Thymeleaf) for web interface at `/forum`, `/auth`, `/admin`
- REST Controllers (JSON API) for programmatic access at `/api/*`

**Business Logic Layer**:
- Service classes handle business rules, authorization checks, and transaction management
- Services validate operations and ensure users can only modify their own content (or admins can moderate)

**Data Access Layer**:
- JPA Repositories abstract database operations
- Custom repository implementations for complex queries (JPQL and native SQL)

**Database Layer**:
- MariaDB with relational schema
- Entities: User, Post, Comment, Folder, Tag, PostView (view tracking)
- Relationships: Many-to-many for likes and tags, one-to-many for posts/comments, self-referential for folder hierarchy

**Security Layer** (cross-cutting):
- JWT authentication filter validates tokens on every request
- Spring Security manages authorization with role-based access control
- BCrypt encrypts passwords

**Key Design Patterns**:
- Repository Pattern for data access
- Service Layer Pattern for business logic
- DTO Pattern for API data transfer
- Soft Delete Pattern for non-destructive deletion
- Builder Pattern (Lombok) for object construction

---

## Security

The application implements several security measures:

- **JWT Authentication**: Stateless authentication using JSON Web Tokens
- **BCrypt Password Hashing**: Passwords encrypted with BCrypt (work factor: 10)
- **HTTP-Only Cookies**: JWT stored in HTTP-only cookies for web app (prevents XSS)
- **Role-Based Access Control**: USER and ADMIN roles with different permissions
- **Input Validation**: Bean Validation (JSR-380) on all DTOs
- **SQL Injection Protection**: JPA parameterized queries
- **Authorization Checks**: Service layer validates user permissions (owner or admin)
- **Soft Delete**: Maintains data integrity and audit trails

**Note for Production**:
- Enable HTTPS and set `secure` flag on cookies
- Store JWT secret in environment variables (not properties files)
- Add rate limiting for login endpoints
- Enable CSRF protection for web forms

---

## Getting Started

### Prerequisites

- Java 17 or higher
- MariaDB 10.6+ or MySQL 8.0+
- Gradle (wrapper included)
- IDE with Lombok support (IntelliJ IDEA, Eclipse, or VS Code)

### Environment Setup

Before running the application, ensure Java is properly configured:

**Windows:**
1. Verify Java 17 is installed: Open Command Prompt and run `java -version`
2. If Java is not found, set JAVA_HOME:
   - Find your Java installation (typically `C:\Program Files\Java\jdk-17` or similar)
   - Open System Properties → Environment Variables
   - Add new System Variable:
     - Variable name: `JAVA_HOME`
     - Variable value: `C:\Program Files\Java\jdk-17` (your actual Java path)
   - Add to Path: `%JAVA_HOME%\bin`
   - Restart your terminal/PowerShell

**Linux/Mac:**
```bash
# Add to ~/.bashrc or ~/.zshrc
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk  # or your Java path
export PATH=$JAVA_HOME/bin:$PATH
```

**Verify setup:**
```bash
# Windows (Command Prompt)
echo %JAVA_HOME%

# Windows (PowerShell)
echo $env:JAVA_HOME

# Linux/Mac
echo $JAVA_HOME

# All platforms - should show Java 17
java -version
```

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd forum-system
```

2. **Create the database**
```sql
CREATE DATABASE forum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **Run database migrations**

The SQL files are migrations that must be executed in order. Run all schema migrations first, then all seed data migrations:

```bash
# Run schema migrations in order (v1 -> v2 -> v3)
mysql -u your_username -p forum_db < src/main/resources/db/schema.sql
mysql -u your_username -p forum_db < src/main/resources/db/schema_v2.sql
mysql -u your_username -p forum_db < src/main/resources/db/schema_v3.sql

# Run seed data migrations in order (v2 -> v3 -> v4 -> v5)
mysql -u your_username -p forum_db < src/main/resources/db/seed-forum-v2.sql
mysql -u your_username -p forum_db < src/main/resources/db/seed-forum-v3.sql
mysql -u your_username -p forum_db < src/main/resources/db/seed-forum-v4.sql
mysql -u your_username -p forum_db < src/main/resources/db/seed-forum-v5.sql
```

**Note**: Each migration builds on the previous one. Skipping files or running them out of order will cause errors.

4. **Configure application secrets**

Create `src/main/resources/application-secrets.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mariadb://localhost:3306/forum_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your_secret_key_at_least_256_bits
jwt.expiration=86400000
```

5. **Run the application**
```bash
# Using Gradle wrapper (Windows)
gradlew.bat bootRun

# Using Gradle wrapper (Linux/Mac)
./gradlew bootRun
```

The application will start on http://localhost:8080

### Accessing the Application

- **Web Interface**: http://localhost:8080/forum
- **Login Page**: http://localhost:8080/auth/login
- **Register**: http://localhost:8080/auth/register
- **Admin Panel**: http://localhost:8080/admin (admin users only)
- **REST API**: http://localhost:8080/api/*

### API Authentication

For REST API access, include the JWT token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

Get a token by logging in via `/api/auth/login`:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"youruser","password":"yourpass"}'
```

---

## Project Structure

```
forum-system/
├── src/main/java/com/team3/forum/
│   ├── controllers/
│   │   ├── mvc/              # Web controllers (Thymeleaf)
│   │   └── rest/             # REST API controllers
│   ├── models/               # JPA entities and DTOs
│   ├── repositories/         # Data access layer
│   ├── services/             # Business logic
│   ├── security/             # JWT and Spring Security config
│   ├── helpers/              # Mappers and utility classes
│   └── exceptions/           # Custom exceptions and handlers
├── src/main/resources/
│   ├── templates/            # Thymeleaf HTML templates
│   ├── static/               # CSS, JS, images
│   ├── db/                   # SQL schema and seed files
│   └── application.properties
└── src/test/                 # Unit tests
```

**Key Files**:
- `SecurityConfig.java` - Spring Security configuration
- `JwtTokenProvider.java` - JWT token generation and validation
- `JwtAuthenticationFilter.java` - Request authentication filter
- `schema_v3.sql` - Latest database schema
- `PostServiceImpl.java` - Post business logic (likes, views, trending)
- `UserServiceImpl.java` - User management and authentication

---

## Testing

Run tests with:
```bash
# Windows
gradlew.bat test

# Linux/Mac
./gradlew test
```

**Note:** If you get "JAVA_HOME is not set" error, see the [Environment Setup](#environment-setup) section above to configure Java.

The project includes unit tests for:
- User service (CRUD, authentication, blocking)
- Post service (CRUD, likes, view tracking)
- Folder service (hierarchy, slug generation)
- Comment service (CRUD, authorization)
- Tag repository (search, creation)

Tests use Mockito for mocking dependencies and JUnit 5 for assertions.

---

## License

This project is developed for educational purposes as part of Telerik Academy A72 Web Development Program.

© 2024-2025 Telerik Academy A72 - Team 3
