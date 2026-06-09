import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dedicated window for displaying session statistics.
 */
public class StatisticsFrame extends JFrame {

    private final StatisticsManager statisticsManager;
    private final DashboardFrame dashboardFrame;

    public StatisticsFrame(Player player, StatisticsManager statisticsManager, DashboardFrame dashboardFrame) {
        super("Smart Number Guessing Game - Statistics");
        this.statisticsManager = statisticsManager;
        this.dashboardFrame = dashboardFrame;

        UIHelper.applyFrameDefaults(this);
        buildUI(player);
        UIHelper.configureFrame(this, 600, 520);
    }

    private void buildUI(Player player) {
        JPanel mainPanel = UIHelper.createMainPanel();
        JPanel card = UIHelper.createCardPanel();
        card.setLayout(new BorderLayout(0, 12));

        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel title = UIHelper.createTitleLabel("Game Statistics");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(UIHelper.HEADING_FONT);

        JLabel playerLabel = UIHelper.createBodyLabel("Player: " + player.getName());
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setForeground(UIHelper.GRAY);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(6));
        headerPanel.add(playerLabel);

        JPanel statsGrid = new JPanel(new GridLayout(9, 1, 8, 10));
        statsGrid.setOpaque(false);
        statsGrid.setPreferredSize(new Dimension(420, 270));
        statsGrid.setMaximumSize(new Dimension(420, 270));

        statsGrid.add(createStatRow("Games Played", String.valueOf(statisticsManager.getGamesPlayed())));
        statsGrid.add(createStatRow("Games Won", String.valueOf(statisticsManager.getGamesWon())));
        statsGrid.add(createStatRow("Games Lost", String.valueOf(statisticsManager.getGamesLost())));
        statsGrid.add(createStatRow("Win Rate", String.format("%.1f%%", statisticsManager.getWinRate())));
        statsGrid.add(createStatRow("Highest Score", String.valueOf(statisticsManager.getHighestScore())));
        statsGrid.add(createStatRow("Total Attempts", String.valueOf(statisticsManager.getTotalAttempts())));
        statsGrid.add(createStatRow("Avg Attempts / Game",
                String.format("%.2f", statisticsManager.getAverageAttemptsPerGame())));
        statsGrid.add(createStatRow("Current Win Streak", String.valueOf(statisticsManager.getCurrentWinStreak())));
        statsGrid.add(createStatRow("Best Win Streak", String.valueOf(statisticsManager.getBestWinStreak())));

        JPanel statsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        statsWrapper.setOpaque(false);
        statsWrapper.setBorder(new EmptyBorder(8, 0, 16, 0));
        statsWrapper.add(statsGrid);

        JButton backButton = UIHelper.createButton("Back to Dashboard");
        backButton.addActionListener(e -> returnToDashboard());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(8, 0, 12, 0));
        buttonPanel.add(backButton);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(statsWrapper, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createStatRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel nameLabel = UIHelper.createStatLabel(label + ":");
        JLabel valueLabel = UIHelper.createBodyLabel(value);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        row.add(nameLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);
        return row;
    }

    private void returnToDashboard() {
        dispose();
        dashboardFrame.refreshStats();
        dashboardFrame.setVisible(true);
    }
}
