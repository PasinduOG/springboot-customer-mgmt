# REST Spring Boot Customer Management System

A RESTful API application built with Spring Boot for managing customer information with MySQL database integration.

---

## ⚠️ **IMPORTANT: Critical Issues Must Be Fixed Before Running!**

**This project currently has 2 critical bugs preventing it from working:**

1. **🔴 Package Naming Inconsistency:** Classes are in `com.java.example.*` but should be in `com.example.*`
   - **Impact:** Spring Boot cannot find entities, repositories, or DTOs
   - **Symptom:** "Table doesn't exist" error, NullPointerException on repository

2. **🔴 Repository Type Mismatch:** Repository uses `String` as ID type but entity uses `Integer`
   - **Impact:** CRUD operations will fail
   - **Symptom:** Type casting errors, findById() failures

**👉 See the [🚀 Quick Fix Guide](#-quick-fix-guide) section below for step-by-step fixes!**

---

## 🚀 Features

- ✅ RESTful API endpoints for customer management
- ✅ MySQL database integration using Spring Data JPA
- ✅ Automatic database table creation with Hibernate
- ✅ Swagger/OpenAPI documentation (SpringDoc)
- ✅ JSON response format with standardized API responses (ApiResponseDto)
- ✅ Exception handling for database operations with proper HTTP status codes
- ✅ Lombok for reduced boilerplate code
- ✅ Input validation for null customer data
- ✅ Structured error messages with success/failure status

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+** (or compatible version)
- **MySQL Workbench** or any MySQL client (optional)

## 🛠️ Technology Stack

- **Spring Boot 3.5.9** - Core framework
- **Spring Data JPA** - Database abstraction layer
- **MySQL Connector 9.1.0** - MySQL database driver
- **Lombok 1.18.42** - Code generation for POJOs
- **SpringDoc OpenAPI 2.8.13** - API documentation (Swagger UI)
- **Maven** - Dependency management and build tool

## 🏗️ Architecture & Design

### Architectural Pattern
This application follows a **Layered Architecture** pattern:

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│     (CustomerController.java)           │
│  - REST Endpoints                       │
│  - Request/Response handling            │
│  - HTTP Status codes                    │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Service Layer                   │
│     (Not yet implemented)               │
│  - Business Logic                       │
│  - Data validation                      │
│  - Transaction management               │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│      Data Access Layer (DAL)            │
│     (CustomerRepository.java)           │
│  - Database operations (CRUD)           │
│  - Spring Data JPA abstraction          │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Domain/Model Layer              │
│     (Customer.java)                     │
│  - Business entities                    │
│  - JPA entity mappings                  │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Database Layer                 │
│         (MySQL Database)                │
│  - customer_model table                 │
└─────────────────────────────────────────┘
```

### Design Patterns Used

1. **Repository Pattern**
   - `CustomerRepository` extends `JpaRepository`
   - Abstracts data access logic from business logic
   - Provides CRUD operations without writing SQL

2. **DTO Pattern (Data Transfer Object)**
   - `ApiResponseDto` standardizes API responses
   - Separates internal models from API contracts
   - Provides consistent response structure

3. **Dependency Injection (IoC)**
   - `@Autowired` for automatic dependency injection
   - Spring manages bean lifecycle
   - Promotes loose coupling

4. **Active Record Pattern** (via JPA)
   - `Customer` entity maps directly to database table
   - Hibernate manages ORM (Object-Relational Mapping)

### Request Flow

```
Client Request
    │
    ▼
┌─────────────────────────────────────┐
│   HTTP POST /customer/add-customer  │
│   Content-Type: application/json    │
│   Body: { name, address, salary }   │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   CustomerController.addCustomer()  │
│   - Receives Customer object        │
│   - Validates (null check)          │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   CustomerRepository.save()         │
│   - JPA entity persistence          │
│   - Transaction management          │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Hibernate ORM                     │
│   - Generates SQL INSERT            │
│   - Executes query                  │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   MySQL Database                    │
│   INSERT INTO customer_model ...    │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   ApiResponseDto                    │
│   - Wraps customer data             │
│   - Adds success status             │
│   - Adds message                    │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   HTTP Response (201 Created)       │
│   Content-Type: application/json    │
│   Body: ApiResponseDto              │
└─────────────────────────────────────┘
```

### Component Responsibilities

| Component | Responsibility | Annotations |
|-----------|---------------|-------------|
| `Main.java` | Application entry point, Spring Boot configuration | `@SpringBootApplication` |
| `CustomerController` | Handle HTTP requests, route to services | `@RestController`, `@RequestMapping` |
| `Customer` | Domain model, database entity | `@Entity`, `@Table`, `@Data` |
| `CustomerRepository` | Data access operations | `extends JpaRepository` |
| `ApiResponseDto` | Standardize API responses | `@AllArgsConstructor`, `@Getter`, `@Setter` |

## 📁 Project Structure

```
rest-springboot-customer-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── Main.java                      # Application entry point (com.example)
│   │   │           ├── controller/
│   │   │           │   └── CustomerController.java    # REST API endpoints (com.example.controller)
│   │   │           └── dto/
│   │   │               └── ApiResponseDto.java        # Response wrapper (com.java.example.dto) ⚠️
│   │   │       └── java/example/                      # ⚠️ Wrong package location
│   │   │           ├── model/
│   │   │           │   └── Customer.java              # JPA Entity (com.java.example.model) ⚠️
│   │   │           └── repository/
│   │   │               └── CustomerRepository.java    # Data access layer (com.java.example.repository) ⚠️
│   │   └── resources/
│   │       └── application.yml                         # Application configuration
│   └── test/
│       └── java/
├── pom.xml                                             # Maven configuration
└── README.md
```

**⚠️ CRITICAL ISSUE:** The project has inconsistent package naming causing component scanning failures!

## ⚙️ Configuration

### Database Setup

1. **Create MySQL Database:**

```sql
CREATE DATABASE customer_management_db;
```

**Optional:** Create a dedicated user for the application:
```sql
CREATE USER 'customer_app'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON customer_management_db.* TO 'customer_app'@'localhost';
FLUSH PRIVILEGES;
```

2. **Update Database Credentials:**

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/customer_management_db
    username: your_mysql_username      # Default: root
    password: your_mysql_password      # Your MySQL password
  jpa:
    hibernate:
      ddl-auto: update                 # Auto-create/update tables
    show-sql: true                     # Log SQL queries in console
    properties:
      hibernate:
        format_sql: true               # Pretty-print SQL queries
```

### Configuration Properties Explained

| Property | Value | Description |
|----------|-------|-------------|
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/customer_management_db` | JDBC connection URL for MySQL |
| `spring.datasource.username` | `root` | MySQL username |
| `spring.datasource.password` | `your_password` | MySQL password |
| `spring.jpa.hibernate.ddl-auto` | `update` | Auto-create/update tables (use `validate` in production) |
| `spring.jpa.show-sql` | `true` | Log all SQL statements to console |
| `spring.jpa.properties.hibernate.format_sql` | `true` | Format SQL for readability |

### Hibernate DDL-Auto Options

| Option | Behavior | Use Case |
|--------|----------|----------|
| `none` | No schema changes | Production (manual migrations) |
| `validate` | Validate schema, no changes | Production (with Flyway/Liquibase) |
| `update` | Update schema if needed | Development (⚠️ never drops columns) |
| `create` | Drop and recreate schema | Testing (⚠️ data loss!) |
| `create-drop` | Drop schema on shutdown | Testing (⚠️ data loss!) |

**Current Setting:** `update` - Automatically creates the `customer_model` table on first run.

### Optional Configuration

**Add server port configuration:**
```yaml
server:
  port: 8080                           # Default HTTP port

spring:
  application:
    name: customer-management-system   # Application name
```

**Add logging configuration:**
```yaml
logging:
  level:
    com.example: DEBUG                 # Debug logs for your app
    org.hibernate.SQL: DEBUG           # SQL query logs
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE  # SQL parameter values
```

**Production-ready configuration:**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/customer_management_db
    username: ${DB_USERNAME:root}      # Environment variable with fallback
    password: ${DB_PASSWORD}           # Environment variable (required)
    hikari:
      maximum-pool-size: 10            # Connection pool size
      minimum-idle: 5
      connection-timeout: 20000        # 20 seconds
  jpa:
    hibernate:
      ddl-auto: validate               # Never auto-modify schema in prod!
    show-sql: false                    # Disable SQL logging in prod
```

## 🏃 Running the Application

### Method 1: Using Maven

```bash
# Navigate to project directory
cd "rest-springboot-customer-management"

# Clean and build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Method 2: Using IDE

1. Open the project in IntelliJ IDEA or Eclipse
2. Run the `Main.java` class
3. The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Base URL
```
http://localhost:8080
```

### Customer Endpoints

#### 1. Add New Customer

**Endpoint:** `POST /customer/add-customer`

**Request Body:**
```json
{
  "name": "John Doe",
  "address": "123 Main Street, New York",
  "salary": 75000.00
}
```

**Success Response (201 Created):**
```json
{
  "message": "Customer Added Successfully",
  "success": true,
  "data": {
    "id": 1,
    "name": "John Doe",
    "address": "123 Main Street, New York",
    "salary": 75000.0
  }
}
```

**Error Response (400 Bad Request):**
```json
{
  "message": "Customer data is required",
  "success": false,
  "data": null
}
```

**Error Response (500 Internal Server Error):**
```json
{
  "message": "Failed to save customer: [error details]",
  "success": false,
  "data": null
}
```

### Response Structure

All API responses follow a standardized format using `ApiResponseDto`:

| Field    | Type    | Description                                      |
|----------|---------|--------------------------------------------------|
| message  | String  | Human-readable message about the operation       |
| success  | boolean | `true` if operation succeeded, `false` otherwise |
| data     | Object  | Response data (customer object on success)       |

## 📚 API Documentation (Swagger)

Once the application is running, access the interactive API documentation:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

The Swagger UI provides:
- Interactive API testing interface
- Request/response examples
- Model schemas
- Authentication configuration (when implemented)

## 💻 Code Examples

### Main Application

```java
package com.example;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info().title("REST Spring Boot Customer Management System")
                        .description("A RESTful API application built with Spring Boot for managing customer information with MySQL database integration.")
                        .version("1.0.0")
        );
    }
}
```

### Controller Implementation

```java
package com.example.controller;

import com.example.dto.ApiResponseDto;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Customer Controller", description = "To manage customer details")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @PostMapping("/add-customer")
    public ResponseEntity<ApiResponseDto> addCustomer(@RequestBody Customer customer) {
        try {
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponseDto("Customer data is required", false)
                );
            }
            repository.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponseDto("Customer Added Successfully", true, customer));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto("Failed to save customer: " + e.getMessage(), false));
        }
    }
}
```

### Customer Entity (⚠️ MUST BE IN com.example.model)

```java
package com.example.model;  // ⚠️ CRITICAL: Must be com.example.model, NOT com.java.example.model

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer_model")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private Double salary;
}
```

### Repository Interface (⚠️ MUST BE IN com.example.repository)

```java
package com.example.repository;  // ⚠️ CRITICAL: Must be com.example.repository, NOT com.java.example.repository

import com.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// ⚠️ CRITICAL: Second generic parameter must be Integer, not String!
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
```

### API Response DTO (⚠️ MUST BE IN com.example.dto)

```java
package com.example.dto;  // ⚠️ CRITICAL: Must be com.example.dto, NOT com.java.example.dto

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ApiResponseDto {
    private String message;
    private boolean success;
    private Object data;

    public ApiResponseDto(String message, boolean success){
        this.message = message;
        this.success = success;
    }
}
```

## 🗃️ Database Schema

The application automatically creates the following table:

**Table Name:** `customer_model`

| Column  | Type         | Constraints                    |
|---------|--------------|--------------------------------|
| id      | INT          | PRIMARY KEY, AUTO_INCREMENT    |
| name    | VARCHAR(255) |                                |
| address | VARCHAR(255) |                                |
| salary  | DOUBLE       |                                |

## 🧪 Testing the API

### Using cURL

```bash
# Add a customer
curl -X POST http://localhost:8080/customer/add-customer \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Jane Smith\",\"address\":\"456 Oak Ave\",\"salary\":65000}"
```

### Using PowerShell

```powershell
# Add a customer
$body = @{
    name = "Jane Smith"
    address = "456 Oak Avenue"
    salary = 65000
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/customer/add-customer" `
  -Method Post `
  -Body $body `
  -ContentType "application/json"
```

### Using Postman

1. Create a new POST request
2. URL: `http://localhost:8080/customer/add-customer`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
```json
{
  "name": "Jane Smith",
  "address": "456 Oak Avenue",
  "salary": 65000
}
```
5. Send the request

## ⚠️ Known Issues & Critical Bugs

### 🔴 CRITICAL: Package Naming Inconsistency

**Issue:** The project has components in different package hierarchies causing Spring Boot component scanning to fail.

**Current Structure:**
- ❌ `Main.java` is in `com.example` package
- ❌ `CustomerController.java` is in `com.example.controller` package  
- ❌ `Customer.java` is in `com.java.example.model` package
- ❌ `CustomerRepository.java` is in `com.java.example.repository` package
- ❌ `ApiResponseDto.java` is in `com.java.example.dto` package

**Problem:** Spring Boot's `@SpringBootApplication` annotation scans only the package where `Main.java` is located (`com.example`) and its sub-packages. Components in `com.java.example.*` will NOT be found!

**Symptoms:**
- `CustomerRepository` bean not found (NullPointerException)
- `Customer` entity not recognized by JPA
- Table creation fails because entity is not scanned
- "Table 'customer_management_db.customer_model' doesn't exist" error

**Solution:** All classes must be in the same package hierarchy. Choose ONE of these options:

**Option 1: Move everything to `com.example` (RECOMMENDED)**
```
com/
  └── example/
      ├── Main.java
      ├── controller/
      │   └── CustomerController.java
      ├── model/
      │   └── Customer.java
      ├── repository/
      │   └── CustomerRepository.java
      └── dto/
          └── ApiResponseDto.java
```

Change package declarations:
- `Customer.java`: `package com.java.example.model;` → `package com.example.model;`
- `CustomerRepository.java`: `package com.java.example.repository;` → `package com.example.repository;`
- `ApiResponseDto.java`: `package com.java.example.dto;` → `package com.example.dto;`

Update imports in `CustomerController.java`:
```java
import com.example.dto.ApiResponseDto;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
```

**Option 2: Move everything to `com.java.example`**
- Move `Main.java` and `CustomerController.java` to `com.java.example` package
- Update all package declarations accordingly

---

### 🔴 CRITICAL: Repository Generic Type Mismatch

**Issue:** `CustomerRepository` extends `JpaRepository<Customer, String>` but Customer ID is `Integer`

**Current Code:**
```java
public interface CustomerRepository extends JpaRepository<Customer, String>
```

**Customer Entity:**
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;  // ID is Integer, not String!
```

**Problem:** Type mismatch will cause runtime errors when using:
- `repository.findById(id)` - expects String but Customer has Integer ID
- `repository.deleteById(id)` - expects String but Customer has Integer ID
- Any ID-based operation will fail or require incorrect type casting

**Solution:** Change repository to use `Integer` as ID type:
```java
public interface CustomerRepository extends JpaRepository<Customer, Integer>
```

---

### 🟡 WARNING: Import Inconsistencies

**Issue:** `CustomerController` imports from `com.example.*` but tries to use classes from `com.java.example.*`

**Current Imports in CustomerController:**
```java
import com.example.dto.ApiResponseDto;         // Wrong path!
import com.example.model.Customer;             // Wrong path!
import com.example.repository.CustomerRepository; // Wrong path!
```

**Actual Class Locations:**
- `ApiResponseDto` is in `com.java.example.dto`
- `Customer` is in `com.java.example.model`
- `CustomerRepository` is in `com.java.example.repository`

**Why It Compiles:** The IDE may have multiple classpath entries or auto-resolution, but this will fail at runtime!

---

### 🟡 Table Name Issue

**Issue:** Entity uses `@Table(name = "customer_model")` but error mentions this exact table doesn't exist

**Root Cause:** This is NOT a table naming issue - it's because the `Customer` entity is not being scanned by Spring Boot due to the package mismatch!

**Current Code:**
```java
@Entity
@Table(name = "customer_model")
public class Customer {
    // ...
}
```

**Solution:** Fix the package issue first (see Critical Issue #1 above). Once the entity is properly scanned, Hibernate will auto-create the table.

## 🔧 Troubleshooting

### Critical Issues - Must Fix First!

**1. ❌ Table doesn't exist error: "Table 'customer_management_db.customer_model' doesn't exist"**

**Root Cause:** Package naming inconsistency prevents Spring Boot from scanning the `Customer` entity!

**Step-by-Step Fix:**

**Step 1:** Fix package declarations in model, repository, and dto classes:

```java
// Customer.java - Change first line from:
package com.java.example.model;
// To:
package com.example.model;

// CustomerRepository.java - Change first line from:
package com.java.example.repository;
// To:
package com.example.repository;

// ApiResponseDto.java - Change first line from:
package com.java.example.dto;
// To:
package com.example.dto;
```

**Step 2:** Move the physical files to correct directory structure:
```
src/main/java/com/example/
├── Main.java ✅ (already correct)
├── controller/
│   └── CustomerController.java ✅ (already correct)
├── model/
│   └── Customer.java (move from com/java/example/model/)
├── repository/
│   └── CustomerRepository.java (move from com/java/example/repository/)
└── dto/
    └── ApiResponseDto.java (move from com/java/example/dto/)
```

**Step 3:** Fix `CustomerRepository` generic type:
```java
// Change from:
public interface CustomerRepository extends JpaRepository<Customer, String>
// To:
public interface CustomerRepository extends JpaRepository<Customer, Integer>
```

**Step 4:** Rebuild and restart:
```powershell
mvn clean install
mvn spring-boot:run
```

**Verification:** Check startup logs for:
```
Hibernate: create table customer_model (...)
```

---

**2. ❌ NullPointerException on CustomerRepository**

**Symptoms:**
```
java.lang.NullPointerException: Cannot invoke "CustomerRepository.save()" because "this.repository" is null
```

**Root Cause:** Same as Issue #1 - `CustomerRepository` is not being scanned by Spring Boot

**Solution:** Follow the fix steps in Issue #1 above

---

**3. ❌ Cannot autowire CustomerRepository**

**Symptoms:**
```
Could not autowire. No beans of 'CustomerRepository' type found.
```

**Root Cause:** Repository is in wrong package (`com.java.example.repository` instead of `com.example.repository`)

**Solution:** Follow the fix steps in Issue #1 above

---

### Common Issues

**4. Cannot connect to database**
- **Problem:** Connection refused or authentication failed
- **Solution:** 
  - Verify MySQL is running: `mysql -u root -p`
  - Check database credentials in `application.yml`
  - Ensure database `customer_management_db` exists
  - Create database if needed:
    ```sql
    CREATE DATABASE customer_management_db;
    ```

**5. Port already in use**
- **Problem:** Port 8080 is already in use
- **Solution:** Add to `application.yml`:
```yaml
server:
  port: 8081
```

**6. Maven build fails**
- **Solution:** 
```powershell
mvn clean install -U
```

**7. Lombok annotations not working**
- **Problem:** Getters/setters not found
- **Solution:** 
  - Enable annotation processing in your IDE
  - IntelliJ: Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing
  - Install Lombok plugin if needed

**8. MySQL Authentication Plugin Error**
- **Problem:** `Unable to load authentication plugin 'caching_sha2_password'`
- **Solution:** Update MySQL Connector dependency to latest version (already using 9.1.0 in pom.xml)

## 🚀 Quick Fix Guide

### ⚡ Fix All Critical Issues Now

To get the application working, apply these fixes in order:

**1. Fix Customer.java**
```powershell
# Change package from com.java.example.model to com.example.model
# Line 1: package com.java.example.model; → package com.example.model;
```

**2. Fix CustomerRepository.java**
```powershell
# Change package from com.java.example.repository to com.example.repository
# Line 1: package com.java.example.repository; → package com.example.repository;
# Fix import: import com.java.example.model.Customer; → import com.example.model.Customer;
# Fix generic type: extends JpaRepository<Customer, String> → extends JpaRepository<Customer, Integer>
```

**3. Fix ApiResponseDto.java**
```powershell
# Change package from com.java.example.dto to com.example.dto
# Line 1: package com.java.example.dto; → package com.example.dto;
```

**4. Rebuild & Run**
```powershell
mvn clean install
mvn spring-boot:run
```

**5. Verify table creation in logs:**
```
Hibernate: create table customer_model (id integer not null auto_increment, address varchar(255), name varchar(255), salary float(53), primary key (id))
```

---

## ✅ Current Implementation Status

**Implemented Features:**
- ✅ Spring Boot 3.5.9 application setup
- ✅ MySQL database connection configured
- ✅ Swagger/OpenAPI documentation with SpringDoc
- ✅ POST `/customer/add-customer` endpoint
- ✅ Null check validation for customer data
- ✅ Database exception handling with `DataAccessException`
- ✅ Standardized JSON responses using `ApiResponseDto`
- ✅ Proper HTTP status codes (201 Created, 400 Bad Request, 500 Internal Server Error)
- ✅ JPA entity mapping with Hibernate
- ✅ Lombok integration for reduced boilerplate

**Known Bugs (MUST FIX):**
- ❌ Package naming inconsistency (`com.java.example` vs `com.example`)
- ❌ Repository generic type mismatch (String instead of Integer)
- ❌ Entity not being scanned by Spring Boot component scan
- ❌ Table not being auto-created due to entity scanning issue

---

## 🚧 Future Enhancements

### Phase 1: Complete CRUD Operations
- [ ] Add GET endpoint to retrieve all customers (`GET /customer`)
- [ ] Add GET endpoint to retrieve customer by ID (`GET /customer/{id}`)
- [ ] Add PUT endpoint to update customer details (`PUT /customer/{id}`)
- [ ] Add DELETE endpoint to remove customers (`DELETE /customer/{id}`)

### Phase 2: Validation & Error Handling
- [ ] Implement field validation using `@Valid` and Bean Validation
  - `@NotNull`, `@NotBlank` for name
  - `@NotBlank` for address
  - `@Min(0)` for salary
- [ ] Add custom exception handling with `@ControllerAdvice`
- [ ] Create custom exception classes (CustomerNotFoundException, etc.)
- [ ] Add validation for duplicate customer names

### Phase 3: Advanced Features
- [ ] Add pagination and sorting for customer list (`@PageableDefault`)
- [ ] Implement search/filter functionality by name, address, or salary range
- [ ] Add DTOs for request/response separation (CustomerRequestDto, CustomerResponseDto)
- [ ] Implement soft delete functionality

### Phase 4: Testing
- [ ] Add unit tests for Controller (JUnit 5, Mockito)
- [ ] Add unit tests for Repository
- [ ] Add integration tests with TestContainers for MySQL
- [ ] Add API documentation tests

### Phase 5: Security & Production
- [ ] Implement Spring Security with JWT authentication
- [ ] Add role-based access control (ADMIN, USER roles)
- [ ] Implement structured logging with SLF4J and Logback
- [ ] Add request/response logging interceptor
- [ ] Add Docker support (Dockerfile, docker-compose.yml)
- [ ] Add environment-specific profiles (dev, test, prod)
- [ ] Implement database migration with Flyway or Liquibase

### Phase 6: Performance & Monitoring
- [ ] Add caching with Spring Cache and Redis
- [ ] Implement API rate limiting
- [ ] Add health check endpoints with Spring Actuator
- [ ] Add metrics and monitoring with Prometheus/Grafana
- [ ] Optimize database queries with proper indexing

## 👨‍💻 Development

### Building for Production

```bash
# Build JAR file
mvn clean package

# Run the JAR
java -jar target/rest-springboot-customer-management-1.0-SNAPSHOT.jar
```

### Code Style

The project uses Lombok annotations to reduce boilerplate code:
- `@Data` - Generates getters, setters, toString, equals, and hashCode
- `@NoArgsConstructor` - Generates no-argument constructor
- `@AllArgsConstructor` - Generates constructor with all fields

## 📄 License

This is an educational project for learning Spring Boot and REST API development.

## 👤 Author

Enterprise Applications Support Sessions

## 🤝 Contributing

This is an educational project. To contribute:
1. Fix the critical package naming issues first
2. Ensure all tests pass
3. Follow Spring Boot best practices
4. Update documentation for any new features

## 📞 Support

For issues or questions:
1. Check the **🚀 Quick Fix Guide** section first
2. Review the **⚠️ Known Issues & Critical Bugs** section
3. Check the **🔧 Troubleshooting** section
4. Review Swagger documentation at `http://localhost:8080/swagger-ui.html`
5. Check application logs for detailed error messages

## 📚 Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [Lombok Documentation](https://projectlombok.org/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

**Version:** 1.0.0  
**Last Updated:** December 25, 2025  
**Status:** ⚠️ Development - Critical bugs identified, fixes required before deployment

**Critical Bugs Count:** 2 (Package naming inconsistency, Repository type mismatch)  
**Priority:** HIGH - Application will not work without fixes

