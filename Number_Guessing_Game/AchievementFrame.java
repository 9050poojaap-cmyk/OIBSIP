import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Displays locked and unlocked achievements with unlock conditions.
 */
public class AchievementFrame extends JFrame {

    private final AchievementManager achievementManager;
    private final DashboardFrame dashboardFrame;

    public AchievementFrame(AchievementManager achievementManager, DashboardFrame dashboardFrame) {
        super("Smart Number Guessing Game - Achievements");
        this.achievementManager = achievementManager;
        this.dashboardFrame = dashboardFrame;

        UIHelper.applyFrameDefaults(this);
        buildUI();
        UIHelper.configureFrame(this, 600, 450);
    }

    private void buildUI() {
        JPanel mainPanel = UIHelper.createMainPanel();
        JPanel card = UIHelper.createCardPanel();
        card.setLayout(new BorderLayout(10, 10));

        JLabel title = UIHelper.createTitleLabel("Achievements");
        title.setFont(UIHelper.HEADING_FONT);

        long unlocked = achievementManager.getUnlockedCount();
        long total = achievementManager.getAllAchievements().size();
        JLabel progressLabel = UIHelper.createBodyLabel(unlocked + " / " + total + " Unlocked");
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressLabel.setForeground(UIHelper.GRAY);

        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(progressLabel);

        JPanel listPanel = new JPanel();
        listPanel.setOpaque(false);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        List<AchievementManager.Achievement> achievements = achievementManager.getAllAchievements();
        for (AchievementManager.Achievement achievement : achievements) {
            listPanel.add(createAchievementCard(achievement));
            listPanel.add(Box.createVerticalStrut(6));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIHelper.ACCENT_BLUE, 1, true));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(520, 260));
        scrollPane.setMaximumSize(new Dimension(520, 260));

        JButton backButton = UIHelper.createButton("Back to Dashboard");
        backButton.addActionListener(e -> returnToDashboard());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createAchievementCard(AchievementManager.Achievement achievement) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setOpaque(true);
        panel.setBackground(achievement.isUnlocked()
                ? new Color(35, 70, 120)
                : new Color(22, 38, 68));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        achievement.isUnlocked() ? UIHelper.SUCCESS_GREEN : UIHelper.DARK_GRAY, 2, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 68));

        String badge = achievement.isUnlocked() ? "UNLOCKED" : "LOCKED";
        JLabel badgeLabel = new JLabel(badge, SwingConstants.CENTER);
        badgeLabel.setFont(UIHelper.SMALL_FONT);
        badgeLabel.setForeground(achievement.isUnlocked() ? UIHelper.SUCCESS_GREEN : UIHelper.GRAY);
        badgeLabel.setPreferredSize(new Dimension(90, 40));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(achievement.getTitle());
        titleLabel.setFont(UIHelper.HEADING_FONT);
        titleLabel.setForeground(achievement.isUnlocked() ? UIHelper.WHITE : UIHelper.GRAY);

        JLabel descLabel = new JLabel(achievement.getDescription());
        descLabel.setFont(UIHelper.SMALL_FONT);
        descLabel.setForeground(UIHelper.GRAY);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descLabel);

        panel.add(badgeLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    private void returnToDashboard() {
        dispose();
        dashboardFrame.setVisible(true);
    }
}
