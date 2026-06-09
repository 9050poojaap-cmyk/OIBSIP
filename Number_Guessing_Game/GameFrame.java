import javax.swing.*;
import java.awt.*;

/**
 * Main gameplay screen with difficulty selection, guessing, hints, and scoring.
 */
public class GameFrame extends JFrame {

    private final Player player;
    private final StatisticsManager statisticsManager;
    private final AchievementManager achievementManager;
    private final GameEngine gameEngine;
    private final DashboardFrame dashboardFrame;

    private JComboBox<GameEngine.Difficulty> difficultyCombo;
    private JLabel difficultyLabel;
    private JLabel scoreLabel;
    private JLabel attemptsLabel;
    private JLabel rangeLabel;
    private JLabel hintsLabel;
    private JLabel adaptiveLabel;
    private JTextField guessField;
    private JTextArea feedbackArea;
    private JButton submitButton;
    private JButton hintButton;
    private JButton playAgainButton;
    private JButton dashboardButton;
    private JPanel endGamePanel;

    private boolean gameStarted;

    public GameFrame(Player player, StatisticsManager statisticsManager,
                     AchievementManager achievementManager, GameEngine gameEngine,
                     DashboardFrame dashboardFrame) {
        super("Smart Number Guessing Game - Play");
        this.player = player;
        this.statisticsManager = statisticsManager;
        this.achievementManager = achievementManager;
        this.gameEngine = gameEngine;
        this.dashboardFrame = dashboardFrame;
        this.gameStarted = false;

        UIHelper.applyFrameDefaults(this);
        buildUI();
        UIHelper.configureFrame(this, 750, 650);
        showDifficultySelection();
    }

    private void buildUI() {
        JPanel mainPanel = UIHelper.createMainPanel();
        JPanel card = UIHelper.createCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = UIHelper.createTitleLabel("Number Guessing Game");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(UIHelper.HEADING_FONT);

        JLabel playerLabel = UIHelper.createBodyLabel("Player: " + player.getName());
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setForeground(UIHelper.GRAY);

        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 8));
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(520, 56));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        difficultyLabel = UIHelper.createStatLabel("Difficulty: Not Selected");
        scoreLabel = UIHelper.createStatLabel("Current Score: " + player.getCurrentScore());
        attemptsLabel = UIHelper.createStatLabel("Remaining Attempts: -");
        rangeLabel = UIHelper.createStatLabel("Range: -");
        hintsLabel = UIHelper.createStatLabel("Hints Remaining: 2");
        adaptiveLabel = UIHelper.createBodyLabel("");
        adaptiveLabel.setForeground(UIHelper.WARNING_ORANGE);
        adaptiveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(difficultyLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(attemptsLabel);
        infoPanel.add(rangeLabel);

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setOpaque(false);
        difficultyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyPanel.add(UIHelper.createBodyLabel("Select Difficulty: "));
        difficultyCombo = UIHelper.createDifficultyCombo();
        difficultyPanel.add(difficultyCombo);

        JButton startRoundButton = UIHelper.createButton("Begin Round");
        startRoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startRoundButton.addActionListener(e -> startGame());

        guessField = UIHelper.createStyledTextField(12);
        guessField.setMaximumSize(new Dimension(200, 36));
        guessField.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessField.setEnabled(false);

        JPanel guessPanel = new JPanel();
        guessPanel.setOpaque(false);
        guessPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessPanel.add(UIHelper.createBodyLabel("Your Guess: "));
        guessPanel.add(guessField);

        submitButton = UIHelper.createButton("Submit Guess");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setEnabled(false);
        submitButton.addActionListener(e -> handleGuess());

        hintButton = UIHelper.createSecondaryButton("Get Hint");
        hintButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintButton.setEnabled(false);
        hintButton.addActionListener(e -> handleHint());

        feedbackArea = UIHelper.createFeedbackArea();
        feedbackArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane feedbackScroll = new JScrollPane(feedbackArea);
        feedbackScroll.setBorder(BorderFactory.createLineBorder(UIHelper.ACCENT_BLUE, 1, true));
        feedbackScroll.setPreferredSize(new Dimension(520, 96));
        feedbackScroll.setMaximumSize(new Dimension(520, 96));
        feedbackScroll.setAlignmentX(Component.CENTER_ALIGNMENT);

        endGamePanel = new JPanel();
        endGamePanel.setOpaque(false);
        endGamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        endGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endGamePanel.setVisible(false);

        playAgainButton = UIHelper.createButton("Play Again");
        playAgainButton.setPreferredSize(new Dimension(150, 38));
        playAgainButton.addActionListener(e -> playAgain());

        dashboardButton = UIHelper.createSecondaryButton("Return to Dashboard");
        dashboardButton.setPreferredSize(new Dimension(190, 38));
        dashboardButton.addActionListener(e -> returnToDashboard());

        endGamePanel.add(playAgainButton);
        endGamePanel.add(dashboardButton);

        JPanel hintsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        hintsRow.setOpaque(false);
        hintsRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintsRow.setMaximumSize(new Dimension(520, 24));
        hintsRow.add(hintsLabel);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(playerLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(infoPanel);
        card.add(Box.createVerticalStrut(4));
        card.add(hintsRow);
        card.add(adaptiveLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(difficultyPanel);
        card.add(Box.createVerticalStrut(6));
        card.add(startRoundButton);
        card.add(Box.createVerticalStrut(8));
        card.add(guessPanel);
        card.add(Box.createVerticalStrut(6));
        card.add(submitButton);
        card.add(Box.createVerticalStrut(6));
        card.add(hintButton);
        card.add(Box.createVerticalStrut(8));
        card.add(feedbackScroll);
        card.add(Box.createVerticalStrut(6));
        card.add(endGamePanel);

        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);

        guessField.addActionListener(e -> {
            if (submitButton.isEnabled()) {
                handleGuess();
            }
        });
    }

    private void showDifficultySelection() {
        appendFeedback("Select a difficulty level and click Begin Round to start.");
    }

    private void startGame() {
        GameEngine.Difficulty selected = (GameEngine.Difficulty) difficultyCombo.getSelectedItem();
        gameEngine.startNewGame(selected);
        gameStarted = true;
        endGamePanel.setVisible(false);

        GameEngine.GameState state = gameEngine.getGameState();
        updateDisplay(state);
        guessField.setEnabled(true);
        submitButton.setEnabled(true);
        hintButton.setEnabled(true);
        guessField.setText("");
        guessField.requestFocus();

        feedbackArea.setText("");
        appendFeedback("A new number has been chosen between " + state.getMinRange()
                + " and " + state.getMaxRange() + ". Good luck, " + player.getName() + "!");

        if (state.getAdaptiveMessage() != null && !state.getAdaptiveMessage().isEmpty()) {
            adaptiveLabel.setText(state.getAdaptiveMessage());
            appendFeedback(state.getAdaptiveMessage());
        } else {
            adaptiveLabel.setText("");
        }
    }

    private void handleGuess() {
        if (!gameStarted) {
            UIHelper.showError(this, "Please begin a round first.");
            return;
        }

        String error = gameEngine.validateGuessInput(guessField.getText());
        if (error != null) {
            UIHelper.showError(this, error);
            guessField.requestFocus();
            return;
        }

        int guess = gameEngine.parseGuess(guessField.getText());
        GameEngine.GuessResult result = gameEngine.submitGuess(guess);
        GameEngine.GameState state = gameEngine.getGameState();
        updateDisplay(state);

        switch (result) {
            case TOO_HIGH:
                appendFeedback("Too High! Try a lower number.");
                break;
            case TOO_LOW:
                appendFeedback("Too Low! Try a higher number.");
                break;
            case CORRECT:
                appendFeedback("Correct Guess! You found the number " + gameEngine.getTargetNumber() + "!");
                appendFeedback("Round Score: +" + state.getLastRoundScore() + " points");
                showAchievementsIfAny();
                endGame(true);
                break;
            case GAME_OVER:
                appendFeedback("Game Over! The correct number was " + gameEngine.getTargetNumber() + ".");
                endGame(false);
                break;
            default:
                break;
        }

        guessField.setText("");
        guessField.requestFocus();
    }

    private void handleHint() {
        if (!gameStarted || !gameEngine.getGameState().isGameActive()) {
            UIHelper.showError(this, "Start an active game before requesting a hint.");
            return;
        }

        String hint = gameEngine.requestHint();
        appendFeedback(hint);
        hintsLabel.setText("Hints Remaining: " + gameEngine.getHintsRemaining());
    }

    private void showAchievementsIfAny() {
        for (String title : achievementManager.getRecentlyUnlocked()) {
            appendFeedback("Achievement Unlocked: " + title + "!");
        }
    }

    private void endGame(boolean won) {
        gameStarted = false;
        guessField.setEnabled(false);
        submitButton.setEnabled(false);
        hintButton.setEnabled(false);
        endGamePanel.setVisible(true);
        scoreLabel.setText("Current Score: " + player.getCurrentScore());

        if (won) {
            appendFeedback("Total Score: " + player.getCurrentScore()
                    + " | Personal Best: " + player.getPersonalBest());
        }
    }

    private void playAgain() {
        feedbackArea.setText("");
        adaptiveLabel.setText("");
        hintsLabel.setText("Hints Remaining: 2");
        showDifficultySelection();
        difficultyCombo.requestFocus();
    }

    private void returnToDashboard() {
        dispose();
        dashboardFrame.refreshStats();
        dashboardFrame.setVisible(true);
    }

    private void updateDisplay(GameEngine.GameState state) {
        difficultyLabel.setText("Difficulty: " + state.getDifficulty().getLabel());
        scoreLabel.setText("Current Score: " + player.getCurrentScore());
        attemptsLabel.setText("Remaining Attempts: " + state.getRemainingAttempts());
        rangeLabel.setText("Range: " + state.getMinRange() + " - " + state.getMaxRange());
        hintsLabel.setText("Hints Remaining: " + gameEngine.getHintsRemaining());
    }

    private void appendFeedback(String message) {
        if (feedbackArea.getText().isEmpty()) {
            feedbackArea.setText(message);
        } else {
            feedbackArea.append("\n" + message);
        }
        feedbackArea.setCaretPosition(feedbackArea.getDocument().getLength());
    }
}
