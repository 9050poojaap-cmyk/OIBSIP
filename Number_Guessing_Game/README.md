# 🎯 Smart Number Guessing Game

A modern desktop-based Number Guessing Game built using **Core Java (Java 17)** and **Java Swing**.

This project demonstrates Object-Oriented Programming (OOP), GUI development, game logic implementation, statistics tracking, achievement systems, and adaptive difficulty mechanisms without using external libraries or frameworks.

---

## ✨ Features

### 🎮 Multiple Difficulty Levels

Choose from three difficulty levels:

| Difficulty | Number Range | Attempts |
|------------|-------------|-----------|
| Easy | 1 - 50 | 10 |
| Medium | 1 - 100 | 7 |
| Hard | 1 - 200 | 5 |

---

### 🏆 Scoring System

- Score awarded based on difficulty level
- Bonus points for winning quickly
- Personal best score tracking
- Session-based score management

---

### 💡 Smart Hint System

Players can use hints during gameplay:

- Even/Odd Hint
- Midpoint Hint
- Nearness Hint (within 10 or 20)

Limited hints encourage strategic guessing.

---

### 📊 Statistics Dashboard

Track your performance through detailed statistics:

- Games Played
- Games Won
- Games Lost
- Win Rate
- Highest Score
- Total Attempts
- Average Attempts per Game
- Current Win Streak
- Best Win Streak

---

### 🏅 Achievement System

Unlock special achievements while playing:

- First Win
- Lucky Guess
- Perfect Predictor
- Master Guesser
- Winning Streak Achievements
- High Score Achievements

Achievements motivate players and make gameplay more engaging.

---

### 🤖 Adaptive Difficulty

The game dynamically adjusts challenge levels based on player performance:

#### Consecutive Wins
- Number range increases
- Available attempts decrease

#### Consecutive Losses
- Additional attempts may be granted

This creates a more personalized gaming experience.

---

### 🖥️ Modern Java Swing GUI

- Professional gaming-themed interface
- Responsive layouts
- Interactive buttons
- Separate windows for:
  - Dashboard
  - Gameplay
  - Statistics
  - Achievements

---

## 🏗️ Project Structure

```text
Number_Guessing_Game
│
├── Main.java
├── Player.java
├── StatisticsManager.java
├── AchievementManager.java
├── GameEngine.java
├── UIHelper.java
│
├── WelcomeFrame.java
├── DashboardFrame.java
├── GameFrame.java
├── StatisticsFrame.java
├── AchievementFrame.java
│
├── README.md
└── .gitignore
```

---

## 👨‍💻 Technologies Used

- Java 17
- Java Swing
- Object-Oriented Programming (OOP)
- Collections Framework
- Exception Handling
- Event Handling
- Git & GitHub

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

## 🎯 Gameplay Flow

1. Enter Player Name
2. Select Difficulty Level
3. Start a New Round
4. Guess the Number
5. Use Hints Strategically
6. Earn Points and Unlock Achievements
7. View Statistics and Track Progress

---

## 📌 Key Concepts Demonstrated

- Encapsulation
- Abstraction
- Modular Design
- GUI Development
- Event Handling
- Session Management
- Game Logic Design
- Statistics Tracking
- Achievement Systems
- Adaptive Difficulty

---

## 🚀 Future Enhancements

- Persistent Score Storage
- Leaderboard System
- Multiplayer Mode
- Sound Effects
- Dark/Light Theme Switching
- Database Integration
- Online Ranking System

---

## 👩‍💻 Author

**Poojaa P**

IT Student | Sri Sairam Engineering College

---

⭐ Thank you for visiting this project repository.
