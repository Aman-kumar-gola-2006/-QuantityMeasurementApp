
## 🟢 UC16 — Database Integration with JDBC

### 🧾 Overview

UC16 enhances the **Quantity Measurement Application** by introducing **persistent storage using JDBC**. 

In **UC15**, the application used an **in-memory cache repository**. UC16 extends this design by integrating a **database repository layer**, enabling:

* ✅ Long-term data persistence
* ✅ Storage & retrieval of measurement operations
* ✅ Data analysis capability

---

## 🏗️ Architecture

The application continues the **N-Tier architecture** introduced in UC15:

```bash id="e4y3f9"
Application Layer
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
Database (H2)
```

---

## 📚 Layers Description

---

### 🔹 Application Layer

#### 📌 Class

`QuantityMeasurementApp`

#### 🎯 Responsibilities

* Initialize repository implementation
* Configure database settings
* Start application execution
* Display measurement history

---

### 🔹 Controller Layer

#### 📌 Class

`QuantityMeasurementController`

#### 🎯 Responsibilities

* Accept input quantities
* Call service methods
* Display results
* Handle user interaction

---

### 🔹 Service Layer

#### 📌 Interface

`IQuantityMeasurementService`

#### 📌 Implementation

`QuantityMeasurementServiceImpl`

#### ⚙️ Supported Operations

* Compare quantities
* Convert units
* Add quantities
* Subtract quantities
* Divide quantities

#### 🎯 Responsibilities

* Perform business logic
* Validate measurement categories
* Convert units before operations
* Persist results via repository

---

### 🔹 Repository Layer

#### 📌 Interface

`IQuantityMeasurementRepository`

#### 📌 Implementations

* `QuantityMeasurementCacheRepository`
* `QuantityMeasurementDatabaseRepository`

#### 📊 Repository Types

| Repository | Description               |
| ---------- | ------------------------- |
| Cache      | In-memory storage (UC15)  |
| Database   | JDBC-based storage (UC16) |

#### 🎯 Responsibilities

* Store measurement operations
* Retrieve history
* Query operations
* Delete records

---

## 🗄️ Database Integration

UC16 introduces **JDBC-based persistence using H2 database**.

### 🔹 Database Configuration

```bash id="2lf0nt"
jdbc:h2:./quantitydb
```

---

### 🔹 Table: `QUANTITY_MEASUREMENT_ENTITY`

| Column        | Description                          |
| ------------- | ------------------------------------ |
| id            | Primary key                          |
| operation     | Operation type (ADD, SUBTRACT, etc.) |
| operand1      | First quantity                       |
| operand2      | Second quantity                      |
| result        | Operation result                     |
| error_message | Error details                        |
| created_at    | Timestamp                            |

---

## ⚙️ Database Utilities

### 🔹 ApplicationConfig

* Loads config from `application.properties`
* Reads DB URL, username, password
* Selects repository implementation

---

### 🔹 ConnectionPool

#### 🎯 Responsibilities

* Maintain connection pool
* Reuse connections
* Improve performance
* Reduce overhead

---

## ⚠️ Exception Handling

### 🔹 `DatabaseException`

#### 🎯 Purpose

* Handle JDBC errors
* Provide meaningful messages
* Prevent application crashes

---

## 🔄 Persistence Flow

Example: **Addition Operation**

```bash id="vlb0gg"
Controller → Service → Repository → Database
```

---

## 🛠️ Technologies Used

| Technology      | Purpose           |
| --------------- | ----------------- |
| Java            | Core logic        |
| Maven           | Build management  |
| JDBC            | DB connectivity   |
| H2 Database     | Embedded database |
| JUnit 5         | Unit testing      |
| Mockito         | Mock testing      |
| SLF4J + Logback | Logging           |

---

## 🚀 Key Features (UC16)

* ✔ JDBC-based persistence
* ✔ H2 embedded database
* ✔ Connection pooling
* ✔ Configurable repository
* ✔ SQL-based storage
* ✔ Secure parameterized queries
* ✔ Logging with SLF4J
* ✔ Extended testing support

---

## 📈 Advantages

| Improvement        | Benefit                |
| ------------------ | ---------------------- |
| Persistent Storage | Data survives restart  |
| JDBC Integration   | Standard DB access     |
| Connection Pooling | Efficient resource use |
| Query Support      | Retrieve history       |
| Schema Design      | Structured data        |
| Scalability        | Handles large data     |

---

## 🧪 Testing

JUnit test cases verify:

* Database connection
* Repository CRUD operations
* SQL execution
* Connection pool behavior
* Data persistence
* Service integration
* Controller flow

✅ All **UC1–UC15 tests still pass** (backward compatibility)

---

## ✅ Postconditions

After UC16:

* ✔ Database persistence enabled
* ✔ Measurement operations stored in DB
* ✔ Dual repository support (Cache + DB)
* ✔ Query-based retrieval available
* ✔ Improved performance via pooling
* ✔ Ready for REST API integration
* ✔ Maven project structure implemented

---

## ⭐ Summary

UC16 transforms the application from an **in-memory system → database-driven system (JDBC)**.

### 🚀 Improvements:

* Persistence
* Scalability
* Maintainability
* Data analysis capability

👉 This prepares the application for **enterprise-level features** like:

* REST APIs
* Distributed systems
* Production deployment

---
