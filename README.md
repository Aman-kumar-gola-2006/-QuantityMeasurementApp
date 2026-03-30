# 📏 Quantity Measurement App

## 🧾 Project Overview

The **Quantity Measurement App** is a **Test-Driven Development (TDD)** based project designed to demonstrate how to build **scalable and maintainable software** by starting simple and progressively adding complexity.

The application focuses on **comparing and converting length measurements** across different units while strictly following:

* ✅ Test Driven Development (TDD)
* ✅ Incremental Development
* ✅ Clean Code Principles
* ✅ DRY (Don't Repeat Yourself)
* ✅ Proper Git Workflow (Feature branches + PR)

The project is built step-by-step through **Use Cases (UCs)**. Each UC introduces a small feature and refactors the design to keep the code **maintainable and extensible**.

---

## 🧪 Development Methodology

This project follows the **TDD Cycle**:

```
🔴 Write failing test
🟢 Write minimal code to pass
🔵 Refactor without breaking tests
```

### 🎯 Benefits

* Safety
* Maintainability
* Scalability

---

## 🌳 Git Workflow Used

We followed a **professional branching strategy**:

```bash
main      → Stable production code
dev       → Integration branch
feature/* → Individual feature branches
```

### 🔁 Development Flow

* Each Use Case is developed in a **feature branch**
* Code is tested locally
* Changes are pushed and a **Pull Request (PR)** is created
* After review, merged into **dev**

---

## 📚 Use Case Implementation

---

### 🟢 UC1 — Feet Equality

#### 🎯 Goal

Compare two **Feet measurements** for equality.

---

#### 🧪 Tests Written

We validated the **equals contract**:

* Same value → Equal
* Different value → Not equal
* Null comparison → False
* Different object type → False
* Same reference → True

---

#### 💻 Implementation

Created a `Feet` class with:

* `value` field
* `equals()` method

---

#### 🧠 Learning Outcome

* Understanding **equality contract**
* First step of **Test-Driven Development (TDD)**

---

### 🟢 UC2 — Inches Equality

#### 🎯 Goal

Support **Inches unit** in addition to Feet.

---

#### 🧪 Tests Written

Repeated the same equality test cases for:

* Inches = Inches

---

#### 💻 Implementation

Created an `Inches` class similar to `Feet`.

---

#### ⚠️ Problem Observed

* Huge **code duplication**
* `Feet` and `Inches` had **identical logic**

---

#### 🧠 Learning Outcome

* Identified **DRY violation (Don't Repeat Yourself)**
* Realized need for **refactoring**
* Learned how to **add new features without modifying existing logic**

---

## ⭐ Summary

This project demonstrates:

* 📌 Strong foundation of **TDD**
* 📌 Clean and maintainable code practices
* 📌 Real-world **Git workflow**
* 📌 Incremental feature development using **Use Cases**

---
