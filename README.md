# REST Spring Boot Customer Management System

A RESTful API application built with Spring Boot for managing customer information with MySQL database integration.

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
- **MySQL Connector** - MySQL database driver
- **Lombok 1.18.42** - Code generation for POJOs
- **SpringDoc OpenAPI 2.8.13** - API documentation (Swagger UI)
- **Maven** - Dependency management and build tool

## 📁 Project Structure

```
rest-springboot-customer-management/
├── src/
│   ├── main/
│   │   ├── java/com/java/example/
│   │   │   ├── Main.java                      # Application entry point
│   │   │   ├── controller/
│   │   │   │   └── CustomerController.java    # REST API endpoints
│   │   │   ├── model/
│   │   │   │   └── Customer.java              # JPA Entity
│   │   │   ├── repository/
│   │   │   │   └── CustomerRepository.java    # Data access layer
│   │   │   └── dto/
│   │   │       └── ApiResponseDto.java        # Response wrapper
│   │   └── resources/
│   │       └── application.yml                 # Application configuration
│   └── test/
│       └── java/
├── pom.xml                                     # Maven configuration
└── README.md
```

## ⚙️ Configuration

### Database Setup

1. **Create MySQL Database:**

```sql
CREATE DATABASE customer_management_db;
```

2. **Update Database Credentials:**

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/customer_management_db
    username: your_mysql_username
    password: your_mysql_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

> **Note:** The `ddl-auto: update` setting automatically creates/updates the database table based on the entity model.

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

### Controller Implementation

```java
@RestController
@Tag(name = "Customer Controller", description = "To manage customer details")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @PostMapping("/add-customer")
    ResponseEntity<ApiResponseDto> addCustomer(@RequestBody Customer customer) {
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

### Customer Entity

```java
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

### API Response DTO

```java
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

## ⚠️ Known Issues

**1. Repository Generic Type Mismatch**
- **Issue:** `CustomerRepository` extends `JpaRepository<Customer, String>` but Customer ID is `Integer`
- **Current Code:** `public interface CustomerRepository extends JpaRepository<Customer, String>`
- **Should Be:** `public interface CustomerRepository extends JpaRepository<Customer, Integer>`
- **Impact:** This may cause issues with `findById()` and other ID-based operations
- **Fix:** Update the repository interface to use `Integer` as the ID type

## 🔧 Troubleshooting

### Common Issues

**1. Table doesn't exist error**
- **Problem:** `Table 'customer_management_db.customer_model' doesn't exist`
- **Solution:** Ensure `spring.jpa.hibernate.ddl-auto=update` is properly configured in `application.yml` (check indentation). Restart the application.

**2. Cannot connect to database**
- **Problem:** Connection refused or authentication failed
- **Solution:** 
  - Verify MySQL is running: `mysql -u root -p`
  - Check database credentials in `application.yml`
  - Ensure database `customer_management_db` exists

**3. Port already in use**
- **Problem:** Port 8080 is already in use
- **Solution:** Add to `application.yml`:
```yaml
server:
  port: 8081
```

**4. Maven build fails**
- **Solution:** 
```bash
mvn clean install -U
```

## ✅ Current Implementation

**CustomerController** includes:
- ✅ POST `/customer/add-customer` - Add new customer with validation
- ✅ Null check validation for customer data
- ✅ Database exception handling with `DataAccessException`
- ✅ Standardized JSON responses using `ApiResponseDto`
- ✅ Proper HTTP status codes (201 Created, 400 Bad Request, 500 Internal Server Error)
- ✅ Swagger documentation with controller tags

**Data Models:**
- ✅ `Customer` entity with JPA annotations and Lombok
- ✅ `ApiResponseDto` for standardized API responses
- ✅ `CustomerRepository` extending JpaRepository

## 🚧 Future Enhancements

Suggested improvements for the application:

- [ ] Add GET endpoint to retrieve all customers (`GET /customer`)
- [ ] Add GET endpoint to retrieve customer by ID (`GET /customer/{id}`)
- [ ] Add PUT endpoint to update customer details (`PUT /customer/{id}`)
- [ ] Add DELETE endpoint to remove customers (`DELETE /customer/{id}`)
- [ ] Implement field validation using `@Valid` and Bean Validation (`@NotNull`, `@NotEmpty`, `@Min`)
- [ ] Add pagination and sorting for customer list
- [ ] Implement custom exception handling with `@ControllerAdvice`
- [ ] Add unit and integration tests (JUnit, Mockito)
- [ ] Implement search/filter functionality
- [ ] Add Spring Security with JWT authentication
- [ ] Implement structured logging with SLF4J
- [ ] Add Docker support with Dockerfile and docker-compose
- [ ] Fix repository generic type (currently `String`, should be `Integer` for ID)

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

## 📞 Support

For issues or questions:
1. Check the Troubleshooting section
2. Review Swagger documentation at `/swagger-ui.html`
3. Check application logs for detailed error messages

---

**Version:** 1.0.0  
**Last Updated:** December 24, 2025  
**Status:** Development - Basic CRUD operations in progress

