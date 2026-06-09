/**
 * Tracks session statistics for the current player.
 */
public class StatisticsManager {

    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int highestScore;
    private int totalAttempts;
    private int currentWinStreak;
    private int bestWinStreak;
    private int currentLossStreak;

    public void recordWin(int attemptsUsed, int scoreEarned) {
        gamesPlayed++;
        gamesWon++;
        totalAttempts += attemptsUsed;
        currentWinStreak++;
        currentLossStreak = 0;
        if (currentWinStreak > bestWinStreak) {
            bestWinStreak = currentWinStreak;
        }
        if (scoreEarned > highestScore) {
            highestScore = scoreEarned;
        }
    }

    public void recordLoss(int attemptsUsed) {
        gamesPlayed++;
        gamesLost++;
        totalAttempts += attemptsUsed;
        currentWinStreak = 0;
        currentLossStreak++;
    }

    public void updateHighestScore(int score) {
        if (score > highestScore) {
            highestScore = score;
        }
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public double getWinRate() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        return (gamesWon * 100.0) / gamesPlayed;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public double getAverageAttemptsPerGame() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        return (double) totalAttempts / gamesPlayed;
    }

    public int getCurrentWinStreak() {
        return currentWinStreak;
    }

    public int getBestWinStreak() {
        return bestWinStreak;
    }

    public int getCurrentLossStreak() {
        return currentLossStreak;
    }
}
