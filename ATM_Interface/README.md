# 🏧 Smart ATM Management System

A modern ATM Simulator built using **Core Java (Java 17)** and **Java Swing**.

This project demonstrates Object-Oriented Programming (OOP) concepts, secure user authentication, transaction management, and desktop GUI development without using any external frameworks or libraries.

---

## ✨ Features

### 🔐 Secure Authentication

* User ID and PIN based login
* Maximum 3 login attempts
* Automatic account lock after repeated failed attempts

### 💰 Banking Operations

* Check Account Balance
* Deposit Money
* Withdraw Money
* Transfer Money between accounts
* Change PIN securely

### 📜 Transaction Management

* Complete transaction history
* Timestamped transaction records
* Transaction validation and error handling

### 🧾 Receipt Generation

* Automatic receipt generation after:

  * Deposit
  * Withdrawal
  * Transfer

### 🛡️ Security Features

* Input validation
* Self-transfer prevention
* Invalid amount protection
* Daily withdrawal limit enforcement

### 🖥️ Modern GUI

* Built using Java Swing
* Banking-themed dashboard
* User-friendly interface
* Responsive desktop application

---

## 🏗️ Project Structure

```text
ATM_Interface
│
├── Main.java
├── ATM.java
├── User.java
├── Transaction.java
├── AuthenticationManager.java
├── UIHelper.java
├── ReceiptGenerator.java
│
├── LoginFrame.java
├── DashboardFrame.java
├── DepositFrame.java
├── WithdrawFrame.java
├── TransferFrame.java
├── HistoryFrame.java
├── ChangePinFrame.java
│
└── README.md
```

---

## 👨‍💻 Technologies Used

* Java 17
* Java Swing
* Object-Oriented Programming (OOP)
* Collections Framework
* Exception Handling
* Git & GitHub

---

## 🚀 How to Run

### Compile

```bash
javac *.java
```

### Run

```bash
java Main
```

---

## 👤 Demo Accounts

| User ID | PIN  | Account Holder |
| ------- | ---- | -------------- |
| U1001   | 1234 | Pooja Sharma   |
| U1002   | 5678 | Rahul Verma    |
| U1003   | 9012 | Aarav Patel    |

---

## 📌 Key Concepts Demonstrated

* Encapsulation
* Abstraction
* Modular Design
* GUI Development
* Event Handling
* Authentication Systems
* Transaction Processing
* Input Validation

---

## 🎯 Future Enhancements

* Database Integration (MySQL)
* Admin Dashboard
* User Registration
* Persistent Transaction Storage
* Email Notifications
* Account Statement Export

---

## 👩‍💻 Author

**Poojaa P**

IT Student

---

⭐ Thank you for visiting this project repository.
