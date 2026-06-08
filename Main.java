import javax.swing.*;

/**
 * Entry point for the Smart ATM Management System.
 * Launches the Java Swing desktop banking interface.
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fall back to default look and feel
        }
        UIHelper.applyGlobalTheme();

        SwingUtilities.invokeLater(() -> {
            try {
                ATM atm = new ATM();
                LoginFrame loginFrame = new LoginFrame(atm);
                loginFrame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Failed to start application: " + ex.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}
