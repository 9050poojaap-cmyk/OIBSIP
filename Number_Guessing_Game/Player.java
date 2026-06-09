/**
 * Represents a player profile for the current session.
 */
public class Player {

    private final String name;
    private int currentScore;
    private int personalBest;

    public Player(String name) {
        this.name = name.trim();
        this.currentScore = 0;
        this.personalBest = 0;
    }

    public String getName() {
        return name;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        if (currentScore > personalBest) {
            personalBest = currentScore;
        }
    }

    public void addScore(int points) {
        setCurrentScore(currentScore + points);
    }

    public int getPersonalBest() {
        return personalBest;
    }

    public void resetCurrentScore() {
        currentScore = 0;
    }
}
