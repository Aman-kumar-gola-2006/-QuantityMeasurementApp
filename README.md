## 🟢 UC18 — JWT + OAuth2 Security Integration 🚀

### 📌 Overview

The **Quantity Measurement App** is a **Spring Boot-based REST API** that supports measurement operations like:

* 📏 Length
* ⚖️ Weight
* 🧴 Volume
* 🌡️ Temperature

UC18 enhances the application with **advanced security and enterprise features**:

* 🔐 JWT Authentication
* 🌐 GitHub OAuth2 Login
* 🗄️ JPA & Database Integration
* 📊 Swagger API Documentation
* ⚡ Robust Exception Handling & Validation

---

## 🎯 Key Features

### 🧮 Core Functionalities

* Compare quantities
* Convert units
* Arithmetic operations (Add, Subtract, Divide)
* Measurement history tracking
* Error tracking & reporting

---

### 🔐 Security Features (UC18)

* ✅ JWT-based Authentication (**Stateless**)
* ✅ GitHub OAuth2 Login
* ✅ Secure REST APIs
* ✅ Custom Authentication Filter
* ✅ Unauthorized access handling (**401 response**)

---

### 🗄️ Database & Persistence

* JPA (Hibernate ORM)
* H2 (Development)
* MySQL (Production ready)
* Indexed queries for better performance

---

### 📊 API & Monitoring

* Swagger UI (API testing)
* Spring Boot Actuator
* Logging & debugging support

---

## 🏗️ Project Structure

```bash id="j1t6fn"
com.app
│
├── config        # Security & Swagger configuration
├── controller    # REST controllers
├── service       # Business logic
├── repository    # JPA repositories
├── model         # Entities & domain models
├── dto           # Request/Response DTOs
├── security      # JWT & OAuth2 components
├── exception     # Global exception handling
└── core          # Measurement logic
```

---

## ⚙️ Tech Stack

| Layer      | Technology                   |
| ---------- | ---------------------------- |
| Backend    | Java, Spring Boot            |
| Security   | Spring Security, JWT, OAuth2 |
| Database   | H2, MySQL                    |
| ORM        | Hibernate (JPA)              |
| API Docs   | Swagger (OpenAPI)            |
| Build Tool | Maven                        |

---

## 🔑 Authentication Flow

### 🔐 1. JWT Login

```bash id="0hnzrc"
POST /auth/login
```

➡️ Returns **JWT Token**

---

### 🌐 2. GitHub OAuth Login

```bash id="k1p6eq"
GET /oauth2/authorization/github
```

➡️ Redirects to GitHub → Returns JWT after login

---

### 🔒 3. Access Protected APIs

Add header:

```bash id="tq7c4x"
Authorization: Bearer <JWT_TOKEN>
```

---

## 📌 API Endpoints

### 🔹 Quantity Operations

| Method | Endpoint                    | Description         |
| ------ | --------------------------- | ------------------- |
| POST   | /api/v1/quantities/compare  | Compare quantities  |
| POST   | /api/v1/quantities/convert  | Convert units       |
| POST   | /api/v1/quantities/add      | Add quantities      |
| POST   | /api/v1/quantities/subtract | Subtract quantities |
| POST   | /api/v1/quantities/divide   | Divide quantities   |

---

### 🔹 History & Reports

| Method | Endpoint                                         |
| ------ | ------------------------------------------------ |
| GET    | /api/v1/quantities/history/operation/{operation} |
| GET    | /api/v1/quantities/history/type/{type}           |
| GET    | /api/v1/quantities/count/{operation}             |
| GET    | /api/v1/quantities/history/errored               |

---

### 🔹 Auth APIs

| Method | Endpoint       |
| ------ | -------------- |
| POST   | /auth/register |
| POST   | /auth/login    |

---

## ⚙️ Configuration

### 🔐 JWT Properties

```properties id="4gr6d9"
jwt.secret=your_secret_key
jwt.expiration=86400000
```

---

### 🌐 GitHub OAuth Config

```properties id="l9u6k3"
spring.security.oauth2.client.registration.github.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_CLIENT_SECRET
spring.security.oauth2.client.registration.github.scope=user:email
```

---

## ▶️ Running the Application

### 🚀 Steps

```bash id="4t0c2f"
git clone https://github.com/vikash8058/QuantityMeasurementApp.git
cd QuantityMeasurementApp
mvn spring-boot:run
```

---

## 📊 Swagger UI

Access API documentation:

```id="l7j9e1"
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Testing

* Unit & Integration tests included
* Security disabled in test profile

### ✔ Covers:

* API endpoints
* Database persistence
* Validation scenarios

---

## ⚠️ Important Notes

* OAuth login must be tested via **browser (not Postman)**
* JWT is required for all protected APIs
* Unauthorized requests return **401 (not redirect)**

---

## ⭐ Summary

UC18 upgrades the application to a **secure, production-ready system**.

### 🚀 Key Improvements:

* Advanced authentication (JWT + OAuth2)
* Secure REST APIs
* Enterprise-level architecture
* Production-ready database integration

👉 Now the system is ready for:

* Full-stack integration
* Deployment on cloud (AWS, Azure)
* Microservices architecture

---
