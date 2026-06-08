import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates and displays ATM transaction receipts with save-to-file support.
 */
public class ReceiptGenerator {

    private ReceiptGenerator() {
    }

    /**
     * Shows a receipt dialog after a successful transaction.
     */
    public static void showReceipt(Component parent, User user, Transaction transaction) {
        if (transaction == null) {
            return;
        }

        String receiptText = buildReceiptText(user, transaction);

        JTextArea textArea = new JTextArea(receiptText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.setForeground(UIHelper.TEXT_DARK);
        textArea.setBackground(UIHelper.PANEL_WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(420, 260));

        Object[] options = {"Save Receipt", "Close"};
        int choice = JOptionPane.showOptionDialog(
                parent,
                scrollPane,
                "Transaction Receipt",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == 0) {
            saveReceipt(parent, receiptText, transaction.getTransactionId());
        }
    }

    private static String buildReceiptText(User user, Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("     SMART ATM MANAGEMENT SYSTEM\n");
        sb.append("           TRANSACTION RECEIPT\n");
        sb.append("========================================\n");
        sb.append(String.format("Account Holder : %s%n", user.getName()));
        sb.append(String.format("User ID        : %s%n", user.getUserId()));
        sb.append("----------------------------------------\n");
        sb.append(String.format("Transaction ID : %s%n", transaction.getTransactionId()));
        sb.append(String.format("Type           : %s%n", transaction.getType()));
        sb.append(String.format("Amount         : Rs.%.2f%n", transaction.getAmount()));
        sb.append(String.format("Date           : %s%n", transaction.getFormattedDate()));
        sb.append(String.format("Time           : %s%n", transaction.getFormattedTime()));
        if (!transaction.getDescription().isEmpty()) {
            sb.append(String.format("Details        : %s%n", transaction.getDescription()));
        }
        sb.append("----------------------------------------\n");
        sb.append(String.format("Balance After  : Rs.%.2f%n", user.getBalance()));
        sb.append("========================================\n");
        sb.append("        Thank you for banking with us!\n");
        return sb.toString();
    }

    private static void saveReceipt(Component parent, String receiptText, String transactionId) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("Receipt_" + transactionId + ".txt"));
        chooser.setDialogTitle("Save Receipt");

        int result = chooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();
        if (!path.toLowerCase().endsWith(".txt")) {
            file = new File(path + ".txt");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(receiptText);
            JOptionPane.showMessageDialog(parent,
                    "Receipt saved successfully:\n" + file.getAbsolutePath(),
                    "Receipt Saved",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to save receipt: " + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
