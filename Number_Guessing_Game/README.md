# Smart Number Guessing Game

A professional Java Swing desktop application that delivers an advanced number guessing experience with statistics tracking, achievements, adaptive difficulty, and a modern gaming-style interface.

## Project Overview

Smart Number Guessing Game challenges players to guess a randomly generated number within a selected difficulty range. The application tracks performance across a session, rewards strategic play with a scoring system, offers limited hints, and adapts difficulty based on winning and losing streaks.

Built with core Java and object-oriented design principles, the project separates game logic from the user interface for maintainability and clarity.

## Features

- **Player Profile** — Session-based player name with welcome messaging, current score, and personal best tracking
- **Three Difficulty Levels** — Easy (1–50, 10 attempts), Medium (1–100, 7 attempts), Hard (1–200, 5 attempts)
- **Scoring System** — Base points by difficulty with bonus for early wins; consecutive win rewards via streak-based play
- **Hint System** — Up to 2 hints per game (even/odd, midpoint comparison, within-range clues)
- **Session Statistics** — Games played/won/lost, win rate, highest score, average attempts, and win streaks
- **Achievements** — Six unlockable badges including First Win, Lucky Guess, Perfect Predictor, and Legend
- **Adaptive Difficulty** — Range and attempt adjustments based on consecutive wins or losses
- **Modern Swing GUI** — Dark blue gaming theme with rounded buttons, hover effects, and readable typography
- **Input Validation** — Error dialogs for empty, non-numeric, and out-of-range guesses

## Technologies Used

| Technology | Purpose |
|------------|---------|
| Java 17 | Core language |
| Java Swing | Desktop GUI |
| OOP Design | Modular architecture with separated concerns |

No database or external libraries are required.

## Project Structure

```
Number_Guessing_Game/
├── Main.java                 # Application entry point
├── GameEngine.java           # Game logic, scoring, hints, adaptive difficulty
├── Player.java               # Player profile and score tracking
├── StatisticsManager.java    # Session statistics
├── AchievementManager.java   # Achievement definitions and unlock logic
├── UIHelper.java             # Shared styling and UI components
├── WelcomeFrame.java         # Player name entry screen
├── DashboardFrame.java       # Main navigation hub
├── GameFrame.java            # Gameplay screen
├── StatisticsFrame.java      # Statistics display window
├── AchievementFrame.java     # Achievement badges window
└── README.md                 # Project documentation
```

## How To Run

### Prerequisites

- [JDK 17](https://adoptium.net/) or later installed
- `java` and `javac` available in your system PATH

### Compile

Open a terminal in the project directory and run:

```bash
javac *.java
```

### Run

```bash
java Main
```

### Quick Start

1. Enter your player name on the welcome screen and click **Start Game**
2. Use the dashboard to start a new game, view statistics, or check achievements
3. Select a difficulty, click **Begin Round**, and submit guesses
4. Use hints sparingly (maximum 2 per game)
5. After each round, choose **Play Again** or **Return to Dashboard**

## Scoring Reference

| Outcome | Points |
|---------|--------|
| Easy Win | +100 |
| Medium Win | +150 |
| Hard Win | +200 |
| Win within first 2 attempts | +50 bonus |

## Achievements

| Achievement | Unlock Condition |
|-------------|------------------|
| First Win | Win your first game |
| Lucky Guess | Win within the first 2 attempts |
| Perfect Predictor | Guess correctly on the first attempt |
| Master Guesser | Win 5 total games |
| Unstoppable | Win 3 consecutive games |
| Legend | Reach a total score above 1000 |

## Adaptive Difficulty

- **3 consecutive wins** — Range increases by 25; attempts decrease by 1
- **2 consecutive losses** — Attempts increase by 1

Adaptive changes are applied when starting a new round and displayed in the game feedback area.

## Future Enhancements

- Persistent storage for player profiles and statistics across sessions
- Leaderboard with top scores
- Sound effects and animations for wins and achievements
- Timed challenge mode
- Multiplayer or turn-based competitive mode
- Additional difficulty presets and custom range configuration
- Dark/light theme toggle

## License

This project is provided for educational and portfolio use.
