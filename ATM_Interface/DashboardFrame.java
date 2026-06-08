import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Streamlined account dashboard with essential account info and navigation.
 */
public class DashboardFrame extends JFrame {

    private final ATM atm;
    private final JLabel balanceLabel;
    private final JLabel dailyRemainingLabel;
    private final JLabel dailyLimitLabel;

    public DashboardFrame(ATM atm) {
        this.atm = atm;
        User user = atm.getCurrentUser();

        UIHelper.configureFrame(this, "Smart ATM Management System - Dashboard", 740, 680, false);
        setLayout(new BorderLayout());

        add(UIHelper.createHeaderPanel("Account Dashboard",
                "Welcome, " + user.getName() + "  |  " + user.getUserId()), BorderLayout.NORTH);

        balanceLabel = UIHelper.createBodyLabel("Current Balance: " + UIHelper.formatCurrency(user.getBalance()));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        balanceLabel.setForeground(UIHelper.PRIMARY);
        balanceLabel.setBorder(new EmptyBorder(8, 0, 0, 0));
        balanceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        dailyRemainingLabel = UIHelper.createBodyLabel("");
        dailyRemainingLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dailyRemainingLabel.setForeground(UIHelper.PRIMARY);
        dailyRemainingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dailyRemainingLabel.setBorder(new EmptyBorder(6, 0, 4, 0));

        dailyLimitLabel = UIHelper.createMutedLabel("");
        dailyLimitLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(UIHelper.BACKGROUND);
        content.setBorder(new EmptyBorder(20, 28, 24, 28));

        content.add(buildInfoPanel(user), BorderLayout.NORTH);
        content.add(buildButtonPanel(), BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                refreshDashboard();
            }
        });

        refreshDashboard();
    }

    private JPanel buildInfoPanel(User user) {
        JPanel infoPanel = new JPanel(new BorderLayout(0, 12));
        infoPanel.setBackground(UIHelper.BACKGROUND);

        JPanel accountCard = UIHelper.createCardPanel();
        accountCard.setLayout(new BoxLayout(accountCard, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = UIHelper.createBodyLabel("Welcome, " + user.getName());
        welcomeLabel.setFont(UIHelper.HEADER_FONT);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        accountCard.add(welcomeLabel);
        accountCard.add(balanceLabel);

        JPanel dailyCard = UIHelper.createCardPanel();
        dailyCard.setLayout(new BoxLayout(dailyCard, BoxLayout.Y_AXIS));

        JLabel dailyTitle = UIHelper.createFormLabel("Daily Withdrawal");
        dailyTitle.setForeground(UIHelper.PRIMARY);
        dailyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        dailyCard.add(dailyTitle);
        dailyCard.add(dailyRemainingLabel);
        dailyCard.add(dailyLimitLabel);

        infoPanel.add(accountCard, BorderLayout.NORTH);
        infoPanel.add(dailyCard, BorderLayout.CENTER);
        return infoPanel;
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout(0, 14));
        buttonPanel.setBackground(UIHelper.BACKGROUND);

        JPanel gridButtons = new JPanel(new GridLayout(3, 2, 14, 14));
        gridButtons.setBackground(UIHelper.BACKGROUND);

        gridButtons.add(wireNavButton("Check Balance", e -> showBalance()));
        gridButtons.add(wireNavButton("Deposit Money", e -> openFrame(new DepositFrame(atm, this))));
        gridButtons.add(wireNavButton("Withdraw Money", e -> openFrame(new WithdrawFrame(atm, this))));
        gridButtons.add(wireNavButton("Transfer Money", e -> openFrame(new TransferFrame(atm, this))));
        gridButtons.add(wireNavButton("Transaction History", e -> openFrame(new HistoryFrame(atm))));
        gridButtons.add(wireNavButton("Change PIN", e -> openFrame(new ChangePinFrame(atm))));

        JButton logoutButton = UIHelper.createLogoutButton("Logout");
        logoutButton.setPreferredSize(new Dimension(0, 50));
        logoutButton.addActionListener(e -> handleLogout());

        buttonPanel.add(gridButtons, BorderLayout.CENTER);
        buttonPanel.add(logoutButton, BorderLayout.SOUTH);
        return buttonPanel;
    }

    private JButton wireNavButton(String text, java.awt.event.ActionListener action) {
        JButton button = UIHelper.createNavButton(text);
        button.setPreferredSize(new Dimension(0, 50));
        button.addActionListener(action);
        return button;
    }

    /**
     * Refreshes all dashboard data from the single active User instance held by ATM.
     */
    public void refreshDashboard() {
        User user = atm.getCurrentUser();
        if (user == null) {
            return;
        }

        balanceLabel.setText("Current Balance: " + UIHelper.formatCurrency(user.getBalance()));
        dailyRemainingLabel.setText("Remaining: " + UIHelper.formatCurrency(user.getRemainingDailyLimit()));
        dailyLimitLabel.setText("Daily Limit: " + UIHelper.formatCurrency(user.getDailyWithdrawalLimit()));
        repaint();
    }

    private void showBalance() {
        User user = atm.getCurrentUser();
        JOptionPane.showMessageDialog(this,
                "Account Holder: " + user.getName() + "\n"
                        + "User ID: " + user.getUserId() + "\n"
                        + "Current Balance: " + UIHelper.formatCurrency(user.getBalance()),
                "Check Balance",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openFrame(JFrame frame) {
        frame.setVisible(true);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            atm.logout();
            dispose();
            new LoginFrame(atm).setVisible(true);
        }
    }
}
