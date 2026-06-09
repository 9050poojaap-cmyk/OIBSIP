import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Shared UI styling utilities for a consistent gaming theme.
 */
public final class UIHelper {

    public static final Color DARK_BLUE = new Color(18, 32, 64);
    public static final Color LIGHT_BLUE = new Color(64, 149, 255);
    public static final Color ACCENT_BLUE = new Color(42, 98, 180);
    public static final Color WHITE = Color.WHITE;
    public static final Color GRAY = new Color(180, 190, 205);
    public static final Color DARK_GRAY = new Color(100, 110, 130);
    public static final Color PANEL_BG = new Color(28, 45, 82);
    public static final Color SUCCESS_GREEN = new Color(46, 204, 113);
    public static final Color WARNING_ORANGE = new Color(241, 196, 15);
    public static final Color ERROR_RED = new Color(231, 76, 60);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);

    private UIHelper() {
    }

    public static void applyFrameDefaults(JFrame frame) {
        frame.getContentPane().setBackground(DARK_BLUE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Applies a fixed, screen-safe size and centers the frame on the display.
     * Must be called after the frame content is built.
     */
    public static void configureFrame(JFrame frame, int width, int height) {
        frame.setResizable(false);
        Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int frameWidth = Math.min(width, screen.width - 40);
        int frameHeight = Math.min(height, screen.height - 40);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
    }

    public static JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BLUE);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(16, 20, 16, 20));
        return panel;
    }

    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(PANEL_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 2, true),
                new EmptyBorder(14, 16, 14, 16)
        ));
        return card;
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(WHITE);
        return label;
    }

    public static JLabel createHeadingLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADING_FONT);
        label.setForeground(LIGHT_BLUE);
        return label;
    }

    public static JLabel createBodyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(WHITE);
        return label;
    }

    public static JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(GRAY);
        return label;
    }

    public static JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(BODY_FONT);
        field.setBackground(new Color(240, 245, 255));
        field.setForeground(DARK_BLUE);
        field.setCaretColor(DARK_BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    public static JButton createButton(String text) {
        return createButton(text, LIGHT_BLUE, ACCENT_BLUE);
    }

    public static JButton createButton(String text, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = getModel().isPressed() ? hoverColor.darker()
                        : getModel().isRollover() ? hoverColor : normalColor;

                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

                g2.setColor(WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Rounded buttons use filled shape only.
            }
        };

        button.setFont(BUTTON_FONT);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.repaint();
            }
        });

        return button;
    }

    public static JButton createSecondaryButton(String text) {
        return createButton(text, DARK_GRAY, GRAY);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static JTextArea createFeedbackArea() {
        JTextArea area = new JTextArea(4, 28);
        area.setFont(SMALL_FONT);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(new Color(20, 35, 65));
        area.setForeground(WHITE);
        area.setBorder(new EmptyBorder(8, 8, 8, 8));
        return area;
    }

    public static JComboBox<GameEngine.Difficulty> createDifficultyCombo() {
        JComboBox<GameEngine.Difficulty> combo = new JComboBox<>(GameEngine.Difficulty.values());
        combo.setFont(BODY_FONT);
        combo.setBackground(new Color(240, 245, 255));
        combo.setForeground(DARK_BLUE);
        return combo;
    }
}
