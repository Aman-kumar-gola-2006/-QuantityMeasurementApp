## 🟢 UC17 — Spring Boot REST API

### 🧾 Overview

UC17 transforms the **Quantity Measurement Application** from a standalone system into a **Spring Boot RESTful service**.

---

## 🔄 What Changed from UC16

| Feature              | UC16 (Before) | UC17 (After)           |
| -------------------- | ------------- | ---------------------- |
| Framework            | Plain Java    | Spring Boot 3.1.0      |
| Database Access      | JDBC (manual) | Spring Data JPA        |
| API Exposure         | None          | REST Endpoints         |
| Configuration        | Manual        | Auto-config            |
| Dependency Injection | Manual        | `@Autowired`           |
| Testing              | JUnit         | MockMvc + SpringBoot   |
| Documentation        | None          | Swagger/OpenAPI        |
| Security             | None          | Spring Security        |
| Server               | None          | Embedded Tomcat (8080) |

---

## 📂 Project Structure

```bash
quantity-measurement-app/
│
├── pom.xml
│
├── src/main/java/com/apps/
│   ├── app/QuantityMeasurementApplication.java
│   ├── config/SecurityConfig.java
│   ├── controller/QuantityMeasurementController.java
│   ├── core/ (NO CHANGE)
│   ├── dto/
│   ├── exception/
│   ├── model/
│   ├── repository/QuantityMeasurementRepository.java
│   └── service/
│
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   └── application-prod.properties
│
├── src/test/
│
└── dump/ (UC16 deprecated files)
```

---

## 🛠️ Technology Stack

| Technology      | Purpose               |
| --------------- | --------------------- |
| Java 17         | Core language         |
| Spring Boot 3.1 | Application framework |
| Spring Web      | REST APIs             |
| Spring Data JPA | ORM (replaces JDBC)   |
| Spring Security | Authentication        |
| H2 / MySQL      | Database              |
| Hibernate       | JPA implementation    |
| HikariCP        | Connection pooling    |
| Swagger/OpenAPI | API documentation     |
| JUnit + Mockito | Testing               |

---

## 🏗️ Architecture

```bash
CLIENT (Browser/Postman)
        ↓
REST Controller (@RestController)
        ↓
Service Layer (@Service)
        ↓
Repository Layer (@Repository, JPA)
        ↓
Database (H2 / MySQL)
```

---

## 📁 Key Files Explanation

### 🔹 Main Class

`QuantityMeasurementApplication.java`

* Entry point
* `@SpringBootApplication`

---

### 🔹 Security

`SecurityConfig.java`

* CSRF disabled
* All endpoints allowed (dev mode)

---

### 🔹 Entity

`QuantityMeasurementEntity.java`

* `@Entity`, `@Table`
* Auto timestamps (`@PrePersist`)

---

### 🔹 Repository

`QuantityMeasurementRepository.java`

* Extends `JpaRepository`
* Auto CRUD
* Custom query methods

---

### 🔹 DTOs

* `QuantityDTO` → validation
* `QuantityInputDTO` → request wrapper
* `QuantityMeasurementDTO` → response

---

### 🔹 Service

`QuantityMeasurementServiceImpl.java`

* Business logic
* DTO ↔ Model conversion
* Error persistence

---

### 🔹 Controller

`QuantityMeasurementController.java`

* `@RestController`
* `/api/v1/quantities`
* Swagger annotations

---

### 🔹 Exception Handling

`GlobalExceptionHandler.java`

* `@ControllerAdvice`
* Centralized error handling

---

## 🌐 REST API Endpoints

**Base URL:**

```
http://localhost:8080/api/v1/quantities
```

| Method | Endpoint                       | Description          |
| ------ | ------------------------------ | -------------------- |
| POST   | /compare                       | Compare quantities   |
| POST   | /convert                       | Convert units        |
| POST   | /add                           | Add quantities       |
| POST   | /subtract                      | Subtract             |
| POST   | /divide                        | Divide               |
| GET    | /history/operation/{operation} | History by operation |
| GET    | /history/type/{type}           | History by type      |
| GET    | /count/{operation}             | Count operations     |
| GET    | /history/errored               | Error records        |

---

## 📥 Sample Request

```json
{
  "thisQuantityDTO": {
    "value": 1.0,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantityDTO": {
    "value": 12.0,
    "unit": "INCHES",
    "measurementType": "LengthUnit"
  }
}
```

---

## 📤 Sample Response

```json
{
  "operation": "compare",
  "resultString": "true",
  "error": false
}
```

---

## 🗄️ Database

### 🔹 Development (H2)

```
jdbc:h2:mem:quantitymeasurementdb
```

Console: `http://localhost:8080/h2-console`

---

### 🔹 Production (MySQL)

```
jdbc:mysql://localhost:3306/quantitymeasurementdb
```

---

## 🔐 Security

* Spring Security enabled
* All endpoints open (dev mode)
* CSRF disabled
* Ready for JWT/OAuth2

---

## ⚠️ Exception Handling

### Error Response Format

```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Quantity Measurement Error",
  "message": "...",
  "path": "..."
}
```

---

## ▶️ How to Run

```bash
mvn spring-boot:run
```

---

## 🧪 Testing

* `@WebMvcTest` → Controller tests
* `@SpringBootTest` → Integration tests
* MockMvc for API testing

---

## 🧠 Key Concepts Learned

* Spring Boot Auto Configuration
* Spring Data JPA (No JDBC code)
* REST API design
* Dependency Injection
* Validation (`@Valid`)
* Exception Handling (`@ControllerAdvice`)
* Spring Security basics
* Swagger Documentation
* Profiles (`dev`, `prod`)

---

## 🔧 CURL Examples

```bash
# Compare
curl -X POST http://localhost:8080/api/v1/quantities/compare \
-H "Content-Type: application/json" \
-d '{...}'

# History
curl http://localhost:8080/api/v1/quantities/history/operation/compare
```

---

## ⭐ Summary

UC17 upgrades the application to a **modern enterprise REST API** using Spring Boot.

### 🚀 Key Improvements

* REST API support
* JPA-based persistence
* Auto configuration
* Security integration
* API documentation (Swagger)

👉 Now the system is ready for:

* Frontend integration
* Microservices architecture
* Production deployment

---


