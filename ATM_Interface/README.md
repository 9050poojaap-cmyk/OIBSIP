# Smart ATM Management System

A professional **Java Swing desktop banking application** built with **Core Java 17**. Demonstrates OOP design, encapsulation, collections, exception handling, and clean separation between business logic and presentation — no frameworks, databases, or external libraries.

---

## Features

| Module | Description |
|--------|-------------|
| **Authentication** | User ID + PIN login; 3 attempts; account lockout |
| **Check Balance** | View current account balance |
| **Deposit** | Add funds with validation and receipt |
| **Withdraw** | Remove funds; daily limit Rs.25,000/day |
| **Transfer** | Send money between users; prevents self-transfer |
| **Transaction History** | Full scrollable history in JTable |
| **Change PIN** | Verify old PIN; validate new 4-digit PIN |
| **Receipt Generator** | Save transaction receipt as `.txt` |
| **Logout** | Secure session end |

### Demo Accounts

| User ID | PIN  | Name          | Balance        |
|---------|------|---------------|----------------|
| U1001   | 1234 | Pooja Sharma  | Rs.25,000.00   |
| U1002   | 5678 | Rahul Verma   | Rs.18,500.50   |
| U1003   | 9012 | Ananya Patel  | Rs.32,000.75   |

---

## Project Structure

```
ATM_Interface/
├── Main.java
├── ATM.java
├── User.java
├── Transaction.java
├── AuthenticationManager.java
├── UIHelper.java
├── ReceiptGenerator.java
├── LoginFrame.java
├── DashboardFrame.java
├── DepositFrame.java
├── WithdrawFrame.java
├── TransferFrame.java
├── HistoryFrame.java
├── ChangePinFrame.java
└── README.md
```

---

## Architecture

```
Main → LoginFrame → DashboardFrame → [Operation Frames]
                         ↓
                        ATM (service)
                         ↓
              AuthenticationManager / User / Transaction
```

---

## How to Run

### Compile

```powershell
cd "c:\Users\Poojaa P\OneDrive\Documents\OIBSIP\ATM_Interface"
javac *.java
```

### Run

```powershell
java Main
```

---

## Author Notes

Focused, interview-ready Core Java ATM application with essential banking features and a clean professional Swing interface.
