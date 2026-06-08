import javax.swing.*;
import java.awt.*;

/**
 * Login window for user authentication with attempt tracking and account lock warnings.
 */
public class LoginFrame extends JFrame {

    private static final int MAX_SESSION_ATTEMPTS = 3;

    private final ATM atm;
    private final JTextField userIdField;
    private final JPasswordField pinField;
    private final JLabel statusLabel;
    private int sessionAttempts;

    public LoginFrame(ATM atm) {
        this.atm = atm;
        this.sessionAttempts = 0;

        UIHelper.configureFrame(this, "Smart ATM Management System - Login", 480, 440, false);
        setLayout(new BorderLayout());

        add(UIHelper.createHeaderPanel("Smart ATM Management System", "Secure Banking Portal"), BorderLayout.NORTH);

        JPanel formPanel = UIHelper.createFormPanel();
        formPanel.add(UIHelper.createMutedLabel("Demo: U1001/1234 | U1002/5678 | U1003/9012"),
                UIHelper.formFullWidthConstraints(0));

        formPanel.add(UIHelper.createFormLabel("User ID"), UIHelper.formLabelConstraints(1));
        userIdField = UIHelper.createFormField();
        formPanel.add(userIdField, UIHelper.formFieldConstraints(1));

        formPanel.add(UIHelper.createFormLabel("PIN"), UIHelper.formLabelConstraints(2));
        pinField = UIHelper.createPasswordField();
        formPanel.add(pinField, UIHelper.formFieldConstraints(2));

        statusLabel = UIHelper.createErrorLabel(" ");
        formPanel.add(statusLabel, UIHelper.formFullWidthConstraints(3));

        add(UIHelper.wrapFormContent(formPanel), BorderLayout.CENTER);

        JButton loginButton = UIHelper.createPrimaryButton("Login");
        JButton exitButton = UIHelper.createSecondaryButton("Exit");
        loginButton.addActionListener(e -> handleLogin());
        exitButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
        pinField.addActionListener(e -> handleLogin());
        add(UIHelper.createButtonBar(loginButton, exitButton), BorderLayout.SOUTH);
    }

    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String pin = new String(pinField.getPassword()).trim();

        if (userId.isEmpty()) {
            statusLabel.setText("Please enter your User ID.");
            return;
        }
        if (pin.isEmpty()) {
            statusLabel.setText("Please enter your 4-digit PIN.");
            return;
        }

        AuthenticationManager auth = atm.getAuthManager();

        if (auth.findUser(userId) == null) {
            statusLabel.setText("Error: Invalid User ID.");
            return;
        }

        if (auth.isAccountLocked(userId)) {
            statusLabel.setText("Account is locked. Contact administrator.");
            JOptionPane.showMessageDialog(this,
                    "This account is locked due to multiple failed login attempts.\n"
                            + "Please contact your bank administrator.",
                    "Account Locked",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        User authenticated = auth.authenticate(userId, pin);
        if (authenticated != null) {
            atm.setCurrentUser(authenticated);
            sessionAttempts = 0;
            statusLabel.setText(" ");
            pinField.setText("");
            dispose();
            new DashboardFrame(atm).setVisible(true);
            return;
        }

        sessionAttempts++;
        statusLabel.setText(auth.getLastAuthMessage());

        if (auth.isAccountLocked(userId)) {
            JOptionPane.showMessageDialog(this,
                    auth.getLastAuthMessage(),
                    "Account Locked",
                    JOptionPane.WARNING_MESSAGE);
            sessionAttempts = 0;
            return;
        }

        if (sessionAttempts >= MAX_SESSION_ATTEMPTS) {
            JOptionPane.showMessageDialog(this,
                    "Login session ended due to multiple failed PIN entries.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            sessionAttempts = 0;
            pinField.setText("");
        }
    }
}
