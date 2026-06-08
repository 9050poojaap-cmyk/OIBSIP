import javax.swing.*;
import java.awt.*;

/**
 * Transfer money window between predefined users with validation.
 */
public class TransferFrame extends JFrame {

    private final ATM atm;
    private final DashboardFrame dashboard;
    private final JTextField receiverField;
    private final JTextField amountField;

    public TransferFrame(ATM atm, DashboardFrame dashboard) {
        this.atm = atm;
        this.dashboard = dashboard;

        UIHelper.configureFrame(this, "Transfer Money", 480, 360, false);
        setLayout(new BorderLayout());

        add(UIHelper.createHeaderPanel("Transfer Money", "Send funds to another account"), BorderLayout.NORTH);

        JPanel formPanel = UIHelper.createFormPanel();
        formPanel.add(UIHelper.createFormLabel("Receiver User ID"), UIHelper.formLabelConstraints(0));
        receiverField = UIHelper.createFormField();
        formPanel.add(receiverField, UIHelper.formFieldConstraints(0));

        formPanel.add(UIHelper.createFormLabel("Amount (Rs.)"), UIHelper.formLabelConstraints(1));
        amountField = UIHelper.createFormField();
        formPanel.add(amountField, UIHelper.formFieldConstraints(1));

        add(UIHelper.wrapFormContent(formPanel), BorderLayout.CENTER);

        JButton transferButton = UIHelper.createAccentButton("Transfer");
        JButton cancelButton = UIHelper.createSecondaryButton("Cancel");
        transferButton.addActionListener(e -> handleTransfer());
        cancelButton.addActionListener(e -> dispose());
        add(UIHelper.createButtonBar(transferButton, cancelButton), BorderLayout.SOUTH);
    }

    private void handleTransfer() {
        try {
            String receiverId = receiverField.getText().trim();
            double amount = ATM.parsePositiveAmount(amountField.getText());
            User sender = atm.getCurrentUser();

            ATM.TransferResult result = atm.transfer(receiverId, amount);
            User receiver = result.getReceiver();

            JOptionPane.showMessageDialog(this,
                    String.format("Transfer of %s to %s (%s) successful!%nYour balance: %s",
                            UIHelper.formatCurrency(amount),
                            receiver.getName(),
                            receiverId,
                            UIHelper.formatCurrency(sender.getBalance())),
                    "Transfer Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            ReceiptGenerator.showReceipt(this, sender, result.getTransaction());
            dashboard.refreshDashboard();
            dispose();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Transfer Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
