import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Shared styling and layout utilities for the Swing GUI layer.
 */
public final class UIHelper {

    public static final Color PRIMARY = new Color(11, 92, 173);
    public static final Color PRIMARY_HOVER = new Color(14, 110, 200);
    public static final Color PRIMARY_PRESSED = new Color(8, 68, 128);

    public static final Color ACCENT = new Color(0, 128, 115);
    public static final Color ACCENT_HOVER = new Color(0, 148, 133);
    public static final Color ACCENT_PRESSED = new Color(0, 108, 97);

    public static final Color SECONDARY_BG = new Color(232, 237, 244);
    public static final Color SECONDARY_HOVER = new Color(214, 222, 234);
    public static final Color SECONDARY_PRESSED = new Color(196, 206, 222);

    public static final Color BACKGROUND = new Color(243, 245, 248);
    public static final Color PANEL_WHITE = Color.WHITE;
    public static final Color BORDER_COLOR = new Color(206, 212, 220);
    public static final Color INPUT_TEXT = new Color(26, 32, 44);

    public static final Color TEXT_DARK = new Color(26, 32, 44);
    public static final Color TEXT_MUTED = new Color(82, 92, 105);
    public static final Color TEXT_ON_PRIMARY = Color.WHITE;
    public static final Color ERROR_TEXT = new Color(176, 32, 40);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font NAV_BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 15);

    private UIHelper() {
    }

    public static void applyGlobalTheme() {
        UIManager.put("Panel.background", BACKGROUND);
        UIManager.put("Label.foreground", TEXT_DARK);
        UIManager.put("Label.font", BODY_FONT);

        UIManager.put("TextField.font", BODY_FONT);
        UIManager.put("TextField.foreground", INPUT_TEXT);
        UIManager.put("TextField.background", PANEL_WHITE);
        UIManager.put("TextField.caretForeground", INPUT_TEXT);
        UIManager.put("TextField.selectionBackground", PRIMARY);
        UIManager.put("TextField.selectionForeground", TEXT_ON_PRIMARY);

        UIManager.put("PasswordField.font", BODY_FONT);
        UIManager.put("PasswordField.foreground", INPUT_TEXT);
        UIManager.put("PasswordField.background", PANEL_WHITE);
        UIManager.put("PasswordField.caretForeground", INPUT_TEXT);
        UIManager.put("PasswordField.selectionBackground", PRIMARY);
        UIManager.put("PasswordField.selectionForeground", TEXT_ON_PRIMARY);

        UIManager.put("Table.font", BODY_FONT);
        UIManager.put("Table.foreground", TEXT_DARK);
        UIManager.put("TableHeader.font", HEADER_FONT);
        UIManager.put("TableHeader.foreground", TEXT_DARK);
        UIManager.put("OptionPane.messageForeground", TEXT_DARK);
        UIManager.put("ProgressBar.foreground", PRIMARY);
        UIManager.put("ProgressBar.selectionForeground", TEXT_ON_PRIMARY);
        UIManager.put("ProgressBar.selectionBackground", TEXT_DARK);
    }

    public static void centerOnScreen(Window window) {
        window.setLocationRelativeTo(null);
    }

    public static void configureFrame(JFrame frame, String title, int width, int height, boolean resizable) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setResizable(resizable);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BACKGROUND);
        centerOnScreen(frame);
    }

    public static JPanel createHeaderPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY);
        panel.setBorder(new EmptyBorder(18, 24, 18, 24));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_ON_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);

        if (subtitle != null && !subtitle.isEmpty()) {
            JLabel subLabel = new JLabel(subtitle);
            subLabel.setFont(BODY_FONT);
            subLabel.setForeground(new Color(210, 225, 245));
            subLabel.setBorder(new EmptyBorder(6, 0, 0, 0));
            subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(subLabel);
        }

        return panel;
    }

    public static JPanel createFormPanel() {
        JPanel panel = UIHelper.createCardPanel();
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    public static GridBagConstraints formLabelConstraints(int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 4, 10, 16);
        gbc.weightx = 0;
        return gbc;
    }

    public static GridBagConstraints formFieldConstraints(int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 4, 10, 4);
        return gbc;
    }

    public static GridBagConstraints formFullWidthConstraints(int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 4, 10, 4);
        return gbc;
    }

    public static JPanel wrapFormContent(JComponent formContent) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(new EmptyBorder(20, 24, 12, 24));
        wrapper.add(formContent, BorderLayout.CENTER);
        return wrapper;
    }

    public static JPanel createButtonBar(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(8, 0, 20, 0));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    public static JButton createPrimaryButton(String text) {
        BankingButton button = new BankingButton(text, PRIMARY, PRIMARY_HOVER, PRIMARY_PRESSED, TEXT_ON_PRIMARY, BUTTON_FONT);
        button.setPreferredSize(new Dimension(140, 44));
        return button;
    }

    public static JButton createNavButton(String text) {
        BankingButton button = new BankingButton(text, PRIMARY, PRIMARY_HOVER, PRIMARY_PRESSED, TEXT_ON_PRIMARY, NAV_BUTTON_FONT);
        button.setPreferredSize(new Dimension(0, 46));
        return button;
    }

    public static JButton createLogoutButton(String text) {
        BankingButton button = new BankingButton(text, SECONDARY_BG, SECONDARY_HOVER, SECONDARY_PRESSED, TEXT_DARK, NAV_BUTTON_FONT);
        button.setPreferredSize(new Dimension(0, 46));
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        BankingButton button = new BankingButton(text, SECONDARY_BG, SECONDARY_HOVER, SECONDARY_PRESSED, TEXT_DARK, BUTTON_FONT);
        button.setPreferredSize(new Dimension(130, 44));
        return button;
    }

    public static JButton createAccentButton(String text) {
        BankingButton button = new BankingButton(text, ACCENT, ACCENT_HOVER, ACCENT_PRESSED, TEXT_ON_PRIMARY, BUTTON_FONT);
        button.setPreferredSize(new Dimension(140, 44));
        return button;
    }

    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADER_FONT);
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JLabel createBodyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JLabel createMutedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(TEXT_MUTED);
        return label;
    }

    public static JLabel createErrorLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(ERROR_TEXT);
        return label;
    }

    public static JTextField createFormField() {
        JTextField field = new JTextField();
        field.setUI(new BasicTextFieldUI());
        configureTextComponent(field);
        return field;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setUI(new BasicPasswordFieldUI());
        configureTextComponent(field);
        return field;
    }

    private static void configureTextComponent(JTextComponent field) {
        field.setFont(BODY_FONT);
        field.setForeground(INPUT_TEXT);
        field.setBackground(PANEL_WHITE);
        field.setCaretColor(INPUT_TEXT);
        field.setOpaque(true);
        field.setSelectedTextColor(TEXT_ON_PRIMARY);
        field.setSelectionColor(PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(10, 12, 10, 12)
        ));
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL_WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return panel;
    }

    public static void styleProgressBar(JProgressBar bar) {
        bar.setFont(BODY_FONT);
        bar.setForeground(PRIMARY);
        bar.setBackground(SECONDARY_BG);
        bar.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        bar.setStringPainted(true);
    }

    public static void styleTable(JTable table) {
        table.setFont(BODY_FONT);
        table.setForeground(TEXT_DARK);
        table.setBackground(PANEL_WHITE);
        table.setRowHeight(30);
        table.setGridColor(BORDER_COLOR);
        table.getTableHeader().setFont(HEADER_FONT);
        table.getTableHeader().setForeground(TEXT_DARK);
        table.getTableHeader().setBackground(SECONDARY_BG);
        table.setSelectionBackground(new Color(200, 220, 245));
        table.setSelectionForeground(TEXT_DARK);
    }

    public static String formatCurrency(double amount) {
        return String.format("Rs.%.2f", amount);
    }

    private static final class BankingButton extends JButton {

        private final Color normalBg;
        private final Color hoverBg;
        private final Color pressedBg;
        private final Color textColor;
        private boolean hovering;
        private boolean pressing;

        BankingButton(String text, Color normalBg, Color hoverBg, Color pressedBg, Color textColor, Font font) {
            super(text);
            this.normalBg = normalBg;
            this.hoverBg = hoverBg;
            this.pressedBg = pressedBg;
            this.textColor = textColor;
            setFont(font);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(12, 20, 12, 20));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        hovering = true;
                        repaint();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    pressing = false;
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (isEnabled() && SwingUtilities.isLeftMouseButton(e)) {
                        pressing = true;
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    pressing = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Color bg = normalBg;
            if (!isEnabled()) {
                bg = new Color(200, 205, 212);
            } else if (pressing) {
                bg = pressedBg;
            } else if (hovering) {
                bg = hoverBg;
            }

            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

            g2.setColor(isEnabled() ? textColor : TEXT_MUTED);
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            String label = getText();
            int textX = (getWidth() - fm.stringWidth(label)) / 2;
            int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(label, textX, textY);
            g2.dispose();
        }
    }
}
