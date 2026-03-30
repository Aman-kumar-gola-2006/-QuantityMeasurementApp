
## 🟢 UC15 — N-Tier Architecture Refactoring

### 🧾 Overview

UC15 refactors the **Quantity Measurement Application** from a **monolithic design** into a **professional N-Tier architecture**.

The application is now structured into multiple layers to improve:

* ✅ Separation of Concerns
* ✅ Maintainability
* ✅ Scalability
* ✅ Testability

---

## 🏗️ Architecture

The application follows a **layered architecture**:

```bash
Application Layer
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
Entity / Model Layer
```

---

## 📚 Layers Description

---

### 🔹 1. Application Layer

#### 📌 Class

`QuantityMeasurementApp`

#### 🎯 Responsibilities

* Initialize components
* Start the application
* Call controller methods

---

### 🔹 2. Controller Layer

#### 📌 Class

`QuantityMeasurementController`

#### 🎯 Responsibilities

* Accept user input
* Call service methods
* Display results

---

### 🔹 3. Service Layer

#### 📌 Interface

`IQuantityMeasurementService`

#### 📌 Implementation

`QuantityMeasurementServiceImpl`

#### ⚙️ Supported Operations

* Compare quantities
* Add quantities
* Subtract quantities
* Divide quantities

---

### 🔹 4. Repository Layer

#### 📌 Interface

`IQuantityMeasurementRepository`

#### 📌 Implementation

`QuantityMeasurementCacheRepository`

#### ⭐ Special Feature

* Uses **Singleton Pattern** to maintain a single instance

---

### 🔹 5. Entity / Model Layer

#### 📦 Classes

* `QuantityDTO`
* `QuantityModel`
* `QuantityMeasurementEntity`

#### 🎯 Purpose

| Type   | Description                     |
| ------ | ------------------------------- |
| DTO    | Transfer data between layers    |
| Model  | Internal service representation |
| Entity | Stores measurement records      |

---

## 🧠 Design Principles Used

* ✔ Separation of Concerns
* ✔ SOLID Principles
* ✔ Dependency Injection

---

## 🏛️ Design Patterns Used

* 🧩 Singleton Pattern
* 🏭 Factory Pattern
* 🎭 Facade Pattern

---

## 🧪 Testing

JUnit test cases are implemented to verify:

* ✅ Service operations
* ✅ Controller flow
* ✅ Repository behavior
* ✅ Edge cases

---

## ⭐ Learning Outcome

* Transition from **Monolithic → Layered Architecture**
* Improved understanding of **enterprise-level design**
* Hands-on experience with **design patterns & SOLID principles**
* Better **testability and scalability**

---

