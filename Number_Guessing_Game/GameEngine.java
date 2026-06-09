import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Core game logic: difficulty, guessing, hints, scoring, and adaptive difficulty.
 */
public class GameEngine {

    public enum Difficulty {
        EASY("Easy", 1, 50, 10, 100),
        MEDIUM("Medium", 1, 100, 7, 150),
        HARD("Hard", 1, 200, 5, 200);

        private final String label;
        private final int baseMin;
        private final int baseMax;
        private final int baseAttempts;
        private final int baseScore;

        Difficulty(String label, int baseMin, int baseMax, int baseAttempts, int baseScore) {
            this.label = label;
            this.baseMin = baseMin;
            this.baseMax = baseMax;
            this.baseAttempts = baseAttempts;
            this.baseScore = baseScore;
        }

        public String getLabel() {
            return label;
        }

        public int getBaseMin() {
            return baseMin;
        }

        public int getBaseMax() {
            return baseMax;
        }

        public int getBaseAttempts() {
            return baseAttempts;
        }

        public int getBaseScore() {
            return baseScore;
        }
    }

    public enum GuessResult {
        TOO_HIGH,
        TOO_LOW,
        CORRECT,
        GAME_OVER
    }

    public static class GameState {
        private final Difficulty difficulty;
        private final int minRange;
        private final int maxRange;
        private final int remainingAttempts;
        private final int attemptsUsed;
        private final int lastRoundScore;
        private final boolean gameActive;
        private final String adaptiveMessage;

        public GameState(Difficulty difficulty, int minRange, int maxRange, int remainingAttempts,
                         int attemptsUsed, int lastRoundScore, boolean gameActive, String adaptiveMessage) {
            this.difficulty = difficulty;
            this.minRange = minRange;
            this.maxRange = maxRange;
            this.remainingAttempts = remainingAttempts;
            this.attemptsUsed = attemptsUsed;
            this.lastRoundScore = lastRoundScore;
            this.gameActive = gameActive;
            this.adaptiveMessage = adaptiveMessage;
        }

        public Difficulty getDifficulty() {
            return difficulty;
        }

        public int getMinRange() {
            return minRange;
        }

        public int getMaxRange() {
            return maxRange;
        }

        public int getRemainingAttempts() {
            return remainingAttempts;
        }

        public int getAttemptsUsed() {
            return attemptsUsed;
        }

        public int getLastRoundScore() {
            return lastRoundScore;
        }

        public boolean isGameActive() {
            return gameActive;
        }

        public String getAdaptiveMessage() {
            return adaptiveMessage;
        }
    }

    private static final int MAX_HINTS = 2;
    private static final int EARLY_WIN_BONUS = 50;
    private static final int ADAPTIVE_RANGE_BONUS = 25;

    private final Player player;
    private final StatisticsManager statisticsManager;
    private final AchievementManager achievementManager;
    private final Random random;

    private Difficulty currentDifficulty;
    private int minRange;
    private int maxRange;
    private int maxAttempts;
    private int remainingAttempts;
    private int targetNumber;
    private int attemptsUsed;
    private int hintsUsed;
    private int lastRoundScore;
    private boolean gameActive;
    private String adaptiveMessage;
    private final List<String> hintHistory;
    private final List<String> availableHintTypes;

    public GameEngine(Player player, StatisticsManager statisticsManager, AchievementManager achievementManager) {
        this.player = player;
        this.statisticsManager = statisticsManager;
        this.achievementManager = achievementManager;
        this.random = new Random();
        this.hintHistory = new ArrayList<>();
        this.availableHintTypes = new ArrayList<>();
        this.adaptiveMessage = "";
    }

    public void startNewGame(Difficulty difficulty) {
        currentDifficulty = difficulty;
        adaptiveMessage = "";
        hintsUsed = 0;
        attemptsUsed = 0;
        lastRoundScore = 0;
        hintHistory.clear();

        minRange = difficulty.getBaseMin();
        maxRange = difficulty.getBaseMax();
        maxAttempts = difficulty.getBaseAttempts();

        applyAdaptiveDifficulty();

        remainingAttempts = maxAttempts;
        targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        gameActive = true;

        prepareHintPool();
    }

    private void applyAdaptiveDifficulty() {
        int winStreak = statisticsManager.getCurrentWinStreak();
        int lossStreak = statisticsManager.getCurrentLossStreak();

        if (winStreak >= 3) {
            maxRange += ADAPTIVE_RANGE_BONUS;
            maxAttempts = Math.max(1, maxAttempts - 1);
            adaptiveMessage = "Adaptive Difficulty Activated";
        }

        if (lossStreak >= 2) {
            maxAttempts += 1;
            if (adaptiveMessage.isEmpty()) {
                adaptiveMessage = "Difficulty Adjusted To Help Player";
            } else {
                adaptiveMessage += " | Difficulty Adjusted To Help Player";
            }
        }
    }

    private void prepareHintPool() {
        availableHintTypes.clear();
        availableHintTypes.add("EVEN_ODD");
        availableHintTypes.add("GREATER_THAN_MID");
        availableHintTypes.add("LESS_THAN_MID");
        availableHintTypes.add("WITHIN_10");
        availableHintTypes.add("WITHIN_20");
        Collections.shuffle(availableHintTypes, random);
    }

    public GuessResult submitGuess(int guess) {
        if (!gameActive) {
            return GuessResult.GAME_OVER;
        }

        attemptsUsed++;
        remainingAttempts--;

        if (guess == targetNumber) {
            gameActive = false;
            lastRoundScore = calculateWinScore();
            player.addScore(lastRoundScore);
            statisticsManager.recordWin(attemptsUsed, lastRoundScore);
            statisticsManager.updateHighestScore(player.getCurrentScore());
            achievementManager.checkAfterWin(
                    attemptsUsed,
                    statisticsManager.getGamesWon(),
                    statisticsManager.getCurrentWinStreak(),
                    player.getCurrentScore()
            );
            return GuessResult.CORRECT;
        }

        if (remainingAttempts <= 0) {
            gameActive = false;
            statisticsManager.recordLoss(attemptsUsed);
            return GuessResult.GAME_OVER;
        }

        return guess > targetNumber ? GuessResult.TOO_HIGH : GuessResult.TOO_LOW;
    }

    public String requestHint() {
        if (!gameActive) {
            return "Game is not active.";
        }
        if (hintsUsed >= MAX_HINTS) {
            return "No hints remaining for this game.";
        }
        if (availableHintTypes.isEmpty()) {
            return "No more hints available.";
        }

        String hintType = availableHintTypes.remove(0);
        String hintText = buildHint(hintType);
        hintsUsed++;
        hintHistory.add(hintText);
        return hintText;
    }

    private String buildHint(String hintType) {
        int midpoint = (minRange + maxRange) / 2;

        switch (hintType) {
            case "EVEN_ODD":
                return targetNumber % 2 == 0
                        ? "Hint: The number is Even."
                        : "Hint: The number is Odd.";
            case "GREATER_THAN_MID":
                if (targetNumber > midpoint) {
                    return "Hint: The number is Greater than the midpoint (" + midpoint + ").";
                }
                return "Hint: The number is NOT greater than the midpoint (" + midpoint + ").";
            case "LESS_THAN_MID":
                if (targetNumber < midpoint) {
                    return "Hint: The number is Less than the midpoint (" + midpoint + ").";
                }
                return "Hint: The number is NOT less than the midpoint (" + midpoint + ").";
            case "WITHIN_10": {
                int low = Math.max(minRange, targetNumber - 5);
                int high = Math.min(maxRange, targetNumber + 5);
                return "Hint: The number is within 10 (between " + low + " and " + high + ").";
            }
            case "WITHIN_20": {
                int low = Math.max(minRange, targetNumber - 10);
                int high = Math.min(maxRange, targetNumber + 10);
                return "Hint: The number is within 20 (between " + low + " and " + high + ").";
            }
            default:
                return "Hint unavailable.";
        }
    }

    private int calculateWinScore() {
        int score = currentDifficulty.getBaseScore();
        if (attemptsUsed <= 2) {
            score += EARLY_WIN_BONUS;
        }
        return score;
    }

    public String validateGuessInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a guess.";
        }
        try {
            int guess = Integer.parseInt(input.trim());
            if (guess < minRange || guess > maxRange) {
                return "Guess must be between " + minRange + " and " + maxRange + ".";
            }
            return null;
        } catch (NumberFormatException ex) {
            return "Please enter a valid number.";
        }
    }

    public int parseGuess(String input) {
        return Integer.parseInt(input.trim());
    }

    public GameState getGameState() {
        return new GameState(
                currentDifficulty,
                minRange,
                maxRange,
                remainingAttempts,
                attemptsUsed,
                lastRoundScore,
                gameActive,
                adaptiveMessage
        );
    }

    public List<String> getHintHistory() {
        return new ArrayList<>(hintHistory);
    }

    public int getHintsRemaining() {
        return MAX_HINTS - hintsUsed;
    }

    public int getTargetNumber() {
        return targetNumber;
    }
}
