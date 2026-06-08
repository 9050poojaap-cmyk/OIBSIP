import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Transaction history window displaying all transactions in a scrollable table.
 */
public class HistoryFrame extends JFrame {

    public HistoryFrame(ATM atm) {
        User user = atm.getCurrentUser();
        List<Transaction> transactions = user.getTransactions();

        UIHelper.configureFrame(this, "Transaction History", 700, 480, true);
        setLayout(new BorderLayout());

        add(UIHelper.createHeaderPanel("Transaction History",
                "Total Transactions: " + user.getTransactionCount()), BorderLayout.NORTH);

        String[] columns = {"Transaction ID", "Type", "Amount (Rs.)", "Date", "Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Transaction txn : transactions) {
            model.addRow(new Object[]{
                    txn.getTransactionId(),
                    txn.getType(),
                    String.format("%.2f", txn.getAmount()),
                    txn.getFormattedDate(),
                    txn.getFormattedTime()
            });
        }

        JTable table = new JTable(model);
        UIHelper.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIHelper.BACKGROUND);
        center.add(scrollPane, BorderLayout.CENTER);

        if (transactions.isEmpty()) {
            JLabel emptyLabel = UIHelper.createMutedLabel("No transactions recorded yet.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            center.add(emptyLabel, BorderLayout.SOUTH);
        }

        add(center, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(UIHelper.BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        JButton closeButton = UIHelper.createSecondaryButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
