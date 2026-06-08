import javax.swing.*;
import java.awt.*;

/**
 * Withdraw money window with balance and daily limit validation.
 */
public class WithdrawFrame extends JFrame {

    private final ATM atm;
    private final DashboardFrame dashboard;
    private final JTextField amountField;

    public WithdrawFrame(ATM atm, DashboardFrame dashboard) {
        this.atm = atm;
        this.dashboard = dashboard;
        User user = atm.getCurrentUser();

        UIHelper.configureFrame(this, "Withdraw Money", 480, 340, false);
        setLayout(new BorderLayout());

        add(UIHelper.createHeaderPanel("Withdraw Money",
                "Available: " + UIHelper.formatCurrency(user.getBalance())), BorderLayout.NORTH);

        JPanel formPanel = UIHelper.createFormPanel();
        formPanel.add(UIHelper.createMutedLabel(buildLimitText(user)), UIHelper.formFullWidthConstraints(0));
        formPanel.add(UIHelper.createFormLabel("Amount (Rs.)"), UIHelper.formLabelConstraints(1));
        amountField = UIHelper.createFormField();
        formPanel.add(amountField, UIHelper.formFieldConstraints(1));

        add(UIHelper.wrapFormContent(formPanel), BorderLayout.CENTER);

        JButton withdrawButton = UIHelper.createAccentButton("Withdraw");
        JButton cancelButton = UIHelper.createSecondaryButton("Cancel");
        withdrawButton.addActionListener(e -> handleWithdraw());
        cancelButton.addActionListener(e -> dispose());
        add(UIHelper.createButtonBar(withdrawButton, cancelButton), BorderLayout.SOUTH);
    }

    private String buildLimitText(User user) {
        return String.format("Daily Limit: %s  |  Used: %s  |  Remaining: %s",
                UIHelper.formatCurrency(user.getDailyWithdrawalLimit()),
                UIHelper.formatCurrency(user.getTodayWithdrawn()),
                UIHelper.formatCurrency(user.getRemainingDailyLimit()));
    }

    private void handleWithdraw() {
        try {
            double amount = ATM.parsePositiveAmount(amountField.getText());
            User user = atm.getCurrentUser();
            Transaction transaction = atm.withdraw(amount);

            JOptionPane.showMessageDialog(this,
                    "Withdrawal successful!\nNew balance: " + UIHelper.formatCurrency(user.getBalance()),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            ReceiptGenerator.showReceipt(this, user, transaction);
            dashboard.refreshDashboard();
            dispose();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Withdrawal Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
