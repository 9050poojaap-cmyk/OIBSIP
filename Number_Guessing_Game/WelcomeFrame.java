import javax.swing.*;
import java.awt.*;

/**
 * Initial screen for entering the player name and starting the session.
 */
public class WelcomeFrame extends JFrame {

    private final JTextField nameField;

    public WelcomeFrame() {
        super("Smart Number Guessing Game - Welcome");
        UIHelper.applyFrameDefaults(this);

        JPanel mainPanel = UIHelper.createMainPanel();
        JPanel card = UIHelper.createCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = UIHelper.createTitleLabel("Smart Number Guessing Game");
        JLabel subtitle = UIHelper.createBodyLabel("Enter your name to begin your adventure");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(UIHelper.GRAY);

        nameField = UIHelper.createStyledTextField(20);
        nameField.setMaximumSize(new Dimension(320, 40));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = UIHelper.createButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> handleStart());

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(18));
        card.add(nameField);
        card.add(Box.createVerticalStrut(18));
        card.add(startButton);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);
        UIHelper.configureFrame(this, 500, 350);

        getRootPane().setDefaultButton(startButton);
        nameField.addActionListener(e -> handleStart());
    }

    private void handleStart() {
        String name = nameField.getText();
        if (name == null || name.trim().isEmpty()) {
            UIHelper.showError(this, "Please enter your name to continue.");
            nameField.requestFocus();
            return;
        }

        Player player = new Player(name.trim());
        StatisticsManager statisticsManager = new StatisticsManager();
        AchievementManager achievementManager = new AchievementManager();
        GameEngine gameEngine = new GameEngine(player, statisticsManager, achievementManager);

        dispose();
        SwingUtilities.invokeLater(() -> {
            DashboardFrame dashboard = new DashboardFrame(player, statisticsManager, achievementManager, gameEngine);
            dashboard.setVisible(true);
        });
    }
}
