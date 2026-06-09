import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages achievement definitions and unlock state for the session.
 */
public class AchievementManager {

    public static class Achievement {
        private final String id;
        private final String title;
        private final String description;
        private boolean unlocked;

        public Achievement(String id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.unlocked = false;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public boolean isUnlocked() {
            return unlocked;
        }

        public void unlock() {
            unlocked = true;
        }
    }

    private final Map<String, Achievement> achievements;
    private final List<String> recentlyUnlocked;

    public AchievementManager() {
        achievements = new LinkedHashMap<>();
        recentlyUnlocked = new ArrayList<>();
        registerAchievements();
    }

    private void registerAchievements() {
        addAchievement("first_win", "First Win", "Win your first game");
        addAchievement("lucky_guess", "Lucky Guess", "Win within the first 2 attempts");
        addAchievement("perfect_predictor", "Perfect Predictor", "Guess correctly on the first attempt");
        addAchievement("master_guesser", "Master Guesser", "Win 5 total games");
        addAchievement("unstoppable", "Unstoppable", "Win 3 consecutive games");
        addAchievement("legend", "Legend", "Reach a total score above 1000");
    }

    private void addAchievement(String id, String title, String description) {
        achievements.put(id, new Achievement(id, title, description));
    }

    public void checkAfterWin(int attemptsUsed, int totalGamesWon, int currentWinStreak, int totalScore) {
        recentlyUnlocked.clear();

        unlockIfNeeded("first_win", totalGamesWon >= 1);
        unlockIfNeeded("lucky_guess", attemptsUsed <= 2);
        unlockIfNeeded("perfect_predictor", attemptsUsed == 1);
        unlockIfNeeded("master_guesser", totalGamesWon >= 5);
        unlockIfNeeded("unstoppable", currentWinStreak >= 3);
        unlockIfNeeded("legend", totalScore > 1000);
    }

    private void unlockIfNeeded(String id, boolean condition) {
        if (condition) {
            Achievement achievement = achievements.get(id);
            if (achievement != null && !achievement.isUnlocked()) {
                achievement.unlock();
                recentlyUnlocked.add(achievement.getTitle());
            }
        }
    }

    public List<Achievement> getAllAchievements() {
        return new ArrayList<>(achievements.values());
    }

    public List<String> getRecentlyUnlocked() {
        return new ArrayList<>(recentlyUnlocked);
    }

    public long getUnlockedCount() {
        return achievements.values().stream().filter(Achievement::isUnlocked).count();
    }
}
