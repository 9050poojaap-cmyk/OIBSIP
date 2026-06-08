import javax.swing.*;
import java.awt.*;

/**
 * Deposit money window with amount validation and receipt generation.
 */
public class DepositFrame extends JFrame {

    private final ATM atm;
    private final DashboardFrame dashboard;
    private final JTextField amountField;

    public DepositFrame(ATM atm, DashboardFrame dashboard) {
        this.atm = atm;
        this.dashboard = dashboard;

        UIHelper.configureFrame(this, "Deposit Money", 440, 300, false);
        setLayout(new BorderLayout());

        add(UIHelper.createHeaderPanel("Deposit Money", "Add funds to your account"), BorderLayout.NORTH);

        JPanel formPanel = UIHelper.createFormPanel();
        formPanel.add(UIHelper.createFormLabel("Amount (Rs.)"), UIHelper.formLabelConstraints(0));
        amountField = UIHelper.createFormField();
        formPanel.add(amountField, UIHelper.formFieldConstraints(0));

        add(UIHelper.wrapFormContent(formPanel), BorderLayout.CENTER);

        JButton depositButton = UIHelper.createAccentButton("Deposit");
        JButton cancelButton = UIHelper.createSecondaryButton("Cancel");
        depositButton.addActionListener(e -> handleDeposit());
        cancelButton.addActionListener(e -> dispose());
        add(UIHelper.createButtonBar(depositButton, cancelButton), BorderLayout.SOUTH);
    }

    private void handleDeposit() {
        try {
            double amount = ATM.parsePositiveAmount(amountField.getText());
            User user = atm.getCurrentUser();
            Transaction transaction = atm.deposit(amount);

            JOptionPane.showMessageDialog(this,
                    "Deposit successful!\nNew balance: " + UIHelper.formatCurrency(user.getBalance()),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            ReceiptGenerator.showReceipt(this, user, transaction);
            dashboard.refreshDashboard();
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Deposit Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
