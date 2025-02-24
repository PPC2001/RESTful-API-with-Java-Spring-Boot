# Building a RESTful API with Java Spring Boot

### Objective:

The goal of this assignment is to evaluate the candidate's understanding of Java Spring Boot, RESTful API design, database integration, and best practices in software development. The candidate will be required to build a simple yet functional Spring Boot application that demonstrates their ability to create a RESTful API, interact with a database, and handle common web development tasks.

------------------------

Tools & Technologies Used:

Java 17 – Programming Language
Spring Boot 3.4.3 – Backend Framework
Spring Data JPA – ORM for Database
PostgreSQL – Relational Database
Spring Validation – Data validation
SpringDoc OpenAPI – Swagger API Documentation
JUnit & Mockito – Unit Testing
Lombok – Reduces boilerplate code
Spring Boot DevTools – Enhances development
Maven – Build and Dependency Management
Docker – Containerization
Docker Compose – Multi-container setup

----------------------
📂 Project Structure

emp-mgmt-system
│── src/main/java/com/emp/mgmt
│   ├── controller        # REST API Endpoints
│   ├── service           # Business Logic
│   ├── repository        # Database Access (Spring Data JPA)
│   ├── exception         # Custom Exception Handling
│   ├── dto               # Data Transfer Objects (DTOs)
│   ├── model             # Entity Classes (JPA)
│   ├── mapper            # Object Mapping (DTO ↔ Entity)
│── src/main/resources
│   ├── application.properties   # Spring Boot Configurations
│── Dockerfile
│── docker-compose.yml
│── README.md
│── pom.xml               # Maven Dependencies

--------------------
**🛠 Design Decisions**


1️⃣ Layered Architecture
The project follows a multi-layered architecture, ensuring a clean separation of concerns:

Controller Layer: Handles HTTP requests and responses.
Service Layer: Contains business logic and interacts with repositories.
Repository Layer: Handles database operations using Spring Data JPA.
Mapper Layer: Converts between DTOs and Entity classes, promoting data abstraction.
Exception Handling: Centralized exception handling using @ControllerAdvice


2️⃣ RESTful API Design
Follows REST principles: Resources are exposed via well-structured endpoints (/api/employees).
Uses HTTP Methods Correctly:
GET → Fetch data.
POST → Create new records.
PUT → Update records.
DELETE → Remove data.
Proper Status Codes: Uses meaningful status codes like 200 OK, 201 Created, 400 Bad Request, 404 Not Found, etc.


3️⃣ DTO Pattern for Data Transfer
The DTO (Data Transfer Object) layer abstracts the entity layer, improving security and flexibility.
It prevents exposing sensitive database schema details.
Mapping is handled using MapStruct, ensuring efficient conversion between entities and DTOs.

4️⃣ Spring Boot & Dependency Injection
Uses Spring Boot 3.4.3 for rapid development and minimal configuration.
Leverages Spring’s Dependency Injection (DI) for better modularity and testability.
Services and repositories are managed as Spring Beans (@Service, @Repository).


5️⃣ Database Integration
Uses PostgreSQL as the relational database.
Spring Data JPA simplifies database operations with minimal boilerplate code.
Uses application.properties for configuring database connection settings.


6️⃣ Exception Handling
Centralized exception handling using @ControllerAdvice.
Custom exception classes ensure granular error control (e.g., EmployeeNotFoundException).
Returns structured error responses for better API usability.

7️⃣ Docker & Containerization
A Dockerfile is included to package the application as a lightweight container.
Uses Docker Compose to define multi-container setup (Spring Boot + PostgreSQL).
This ensures consistent deployment across different environments.

8️⃣ API Documentation (Swagger UI)
Uses SpringDoc OpenAPI (springdoc-openapi-starter-webmvc-ui) for API documentation.
Developers can explore API endpoints via Swagger UI (http://localhost:7070/swagger-ui.html).

9️⃣ Testing Strategy
Uses JUnit and Mockito for unit and integration testing.
Mocks dependencies in service layer tests for isolated testing.
Ensures high code coverage for reliability.


-----------------
🛠 Setup & Installation

1️⃣ Clone the Repository

git clone https://github.com/yourusername/emp-mgmt-system.git
cd emp-mgmt-system

2️⃣ Build the Application
mvn clean install

3️⃣ Run with Docker
docker-compose up -d

4️⃣ Run Locally Without Docker
Ensure PostgreSQL is running and update application.properties:

**spring.datasource.url=jdbc:postgresql://localhost:5432/emp_db
spring.datasource.username=emp_user
spring.datasource.password=emp_pass**

📜 Swagger API Docs
After running the app, open:
📌 Swagger UI: http://localhost:7070/swagger-ui/index.html

📌 Swagger Screenshot:

![image](https://github.com/user-attachments/assets/c6d88860-80d5-4e91-880e-cb92a5898166)

📌 Postman Screenshot:
![image](https://github.com/user-attachments/assets/977cd25a-df23-490b-91cf-bc181359c185)

📌 PostgreSQL Screenshot:
![image](https://github.com/user-attachments/assets/ba15580d-48c3-4d6d-9de8-90ee1e80d1aa)

📌 Docker Image Screenshot:
![image](https://github.com/user-attachments/assets/d497818e-025b-4475-b93c-89091b595d2d)

