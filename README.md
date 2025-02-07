# Task-Management-API - Documentation

## Overview
The Task Management API supports: ✔ Task Prioritization, 📌 Status Tracking, 🔍 Advanced Filtering and 🔐 Secure JWT Authentication


## Pre-requisites
- _**Bearer Token:**_ Access user-specific endpoints using bearer token.
- **_Database:_** MySQL, with the database name taskdb.
- **_Sample Requests:_** Available at resources/test-sample/Task Management.postman_collection.json.

## Register User & Login
- Register the user with username and password `POST /user/register`
- login with the same username and password `POST /auth/login`
- Obtain JWT Token from the login response object
- Add the JWT token to the `Authorization` header for subsequent requests
- Add the username to the header; it should be the log-in username.`username: your_username`

## This guide provides comprehensive details on:
- Features
- Technologies Used
- Architecture Design
- Setup & Installation
- Running Tests
- API Endpoints
- Security Considerations

## Features
- **Authentication**: JWT-based authentication for user registration and login.
- **CRUD Operations**: Create, Read operations for users and tasks.
- **Pagination and Filtering**: Support for filtering by tasks based on priority, status and deadline.

## Technologies Used
- **Java 17
- **Spring Boot v3+
- **Spring Security** for authentication and authorization
- **JWT (JSON Web Tokens)** for secure API access
- **Spring Data JPA** for database interaction
- **Hibernate** ORM
- **PostgreSQL** (Relational database)
- **BCrypt** for password hashing
- **Swagger/OpenAPI** for API documentation
- **JUnit** and **Mockito** for unit and integration tests
- **Maven** for dependency management

## Architecture Design

The application follows a **layered architecture** pattern, which separates concerns into different layers to promote maintainability, scalability, and testability. Below is a description of the key components:

### 1. **Layered Architecture (N-tier Architecture)**

- **Controller Layer**: Handles HTTP requests and responses, with each endpoint defined in a controller. The controller delegates business logic to the service layer.
- **Service Layer**: Contains core business logic, such as creating task, updating task, deleting task, filtering task based priority, status and deadline and retrieving all tasks. The service layer also handles validation, exception handling, and implements business rules.
- **Repository Layer**: Interacts with the database using **Spring Data JPA**. Repositories extend `JpaRepository` to offer CRUD functionality and custom queries for entities like `User` and `Task`.
- **Security Layer**: Manages authentication and authorization using **Spring Security** and **JWT-based authentication**.
- **DTO (Data Transfer Object)**: Defines the data structure for requests and responses to ensure that entity models (e.g., `User`, `Trask`) are not exposed directly to clients.

### 2. **Entity Relationships**

Entities are designed for relational integrity:

- **User** has a one-to-many relationship with **Task** (one user can create multiple tasks).

### 3. **Authentication & Authorization**

The application uses **JWT** for stateless authentication. After a user logs in, a JWT token is generated and returned to the client. This token must be included in the `Authorization` header of subsequent requests to access protected resources.

#### Authentication Flow:

1. **Login**:
- User submits username and password via the `/auth/login` endpoint.
- On successful login, a JWT token is generated and returned.

2. **Accessing Protected Resources**:
- Include the JWT token in the `Authorization` header as `Bearer <jwt_token>`.
- Use this token to authenticate requests to protected endpoints.

#### Password Security:

Passwords are securely hashed using **BCrypt** before being stored in the database.

### 3. **Exception Handling**

Global exception handling is implemented using `@ControllerAdvice` to manage errors consistently across the application. Standard HTTP status codes and messages are returned for common error scenarios:
- **400 Bad Request**: Invalid or missing parameters.
- **401 Unauthorized**: Invalid or missing JWT token.
- **404 Not Found**: Resource not found.

### 4. **Database Design**

The database schema is designed with relational integrity in mind. Key tables include:

- **User Table**: Stores user data, such as username and hashed password.
- **Transaction Table**: Stores transaction details, including `title`, `description`, `priority`, `status`, `deadline` and `user`.
---

## Setup & Installation

1. **Clone the repository:**
    ```bash
   https://github.com/Albymens/Task-Management-API.git
   [📂 **Source Code**](https://github.com/Albymens/Task-Management-API.git)
    cd Task-Management-API
    ```

2. **Configure database:**
- Configure your **MySql** database in the `application.properties` or `application.yml` file.
- Example configuration for PostgreSQL:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/taskdb
  spring.datasource.username=your_db_user
  spring.datasource.password=your_db_password
  spring.jpa.hibernate.ddl-auto=update
  spring.datasource.driver-class-name=org.postgresql.Driver
  spring.application.name=user-transaction-tracker
  ```

3. **Build and run the application:**
   Using **Maven**:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Access the API:**
   Once the application is running, you can access the API at `http://localhost:8080`.

5. **Test the endpoints:**
- API documentation is available via **Swagger** at `http://localhost:8080/swagger-ui.html`.

---

## Running Tests

To run unit tests and integration tests, use the following command:

```bash
mvn test
```

This will run all tests using **JUnit 5** and **Mockito**.

---

## API Endpoints
For detailed API endpoints, refer to Swagger UI or the Postman collection:

Swagger Documentation (Local):
👉 http://localhost:8080/swagger-ui.html
Postman Collection:
📂 Available in resources/test-sample/User Transaction Tracker.postman_collection.json
Live API (Deployed on Fly.io):
[🌍 Swagger UI - Live](https://task-management-api-z15qw.fly.dev/swagger-ui/index.html)


## Security Considerations

- **JWT Authentication**: The API uses **JWT** for stateless authentication. JWT tokens are generated upon user login and must be included in subsequent requests for access to protected resources.
- **Password Security**: Passwords are securely hashed using **BCrypt** before being stored in the database.
---

