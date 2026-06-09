import javax.swing.*;
import java.awt.*;

/**
 * Main hub showing player summary and navigation options.
 */
public class DashboardFrame extends JFrame {

    private final Player player;
    private final StatisticsManager statisticsManager;
    private final AchievementManager achievementManager;
    private final GameEngine gameEngine;

    private JLabel welcomeLabel;
    private JLabel bestScoreLabel;
    private JLabel gamesPlayedLabel;
    private JLabel winStreakLabel;
    private JLabel currentScoreLabel;

    public DashboardFrame(Player player, StatisticsManager statisticsManager,
                          AchievementManager achievementManager, GameEngine gameEngine) {
        super("Smart Number Guessing Game - Dashboard");
        this.player = player;
        this.statisticsManager = statisticsManager;
        this.achievementManager = achievementManager;
        this.gameEngine = gameEngine;

        UIHelper.applyFrameDefaults(this);
        buildUI();
        UIHelper.configureFrame(this, 650, 500);
        refreshStats();
    }

    private void buildUI() {
        JPanel mainPanel = UIHelper.createMainPanel();
        JPanel card = UIHelper.createCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        welcomeLabel = UIHelper.createTitleLabel("");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new GridLayout(4, 1, 8, 8));
        statsPanel.setMaximumSize(new Dimension(420, 130));
        statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bestScoreLabel = UIHelper.createStatLabel("");
        gamesPlayedLabel = UIHelper.createStatLabel("");
        winStreakLabel = UIHelper.createStatLabel("");
        currentScoreLabel = UIHelper.createStatLabel("");

        statsPanel.add(bestScoreLabel);
        statsPanel.add(currentScoreLabel);
        statsPanel.add(gamesPlayedLabel);
        statsPanel.add(winStreakLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4, 1, 8, 8));
        buttonPanel.setMaximumSize(new Dimension(280, 188));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newGameBtn = UIHelper.createButton("Start New Game");
        JButton statsBtn = UIHelper.createButton("Statistics");
        JButton achievementsBtn = UIHelper.createButton("Achievements");
        JButton exitBtn = UIHelper.createSecondaryButton("Exit");

        newGameBtn.addActionListener(e -> openGameFrame());
        statsBtn.addActionListener(e -> openStatisticsFrame());
        achievementsBtn.addActionListener(e -> openAchievementFrame());
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameBtn);
        buttonPanel.add(statsBtn);
        buttonPanel.add(achievementsBtn);
        buttonPanel.add(exitBtn);

        card.add(welcomeLabel);
        card.add(Box.createVerticalStrut(14));
        card.add(statsPanel);
        card.add(Box.createVerticalStrut(16));
        card.add(buttonPanel);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);
    }

    public void refreshStats() {
        welcomeLabel.setText("Welcome, " + player.getName() + "!");
        bestScoreLabel.setText("Personal Best Score: " + player.getPersonalBest());
        currentScoreLabel.setText("Current Score: " + player.getCurrentScore());
        gamesPlayedLabel.setText("Games Played: " + statisticsManager.getGamesPlayed());
        winStreakLabel.setText("Current Win Streak: " + statisticsManager.getCurrentWinStreak());
    }

    private void openGameFrame() {
        GameFrame gameFrame = new GameFrame(player, statisticsManager, achievementManager, gameEngine, this);
        gameFrame.setVisible(true);
        setVisible(false);
    }

    private void openStatisticsFrame() {
        StatisticsFrame statsFrame = new StatisticsFrame(player, statisticsManager, this);
        statsFrame.setVisible(true);
        setVisible(false);
    }

    private void openAchievementFrame() {
        AchievementFrame achievementFrame = new AchievementFrame(achievementManager, this);
        achievementFrame.setVisible(true);
        setVisible(false);
    }
}
