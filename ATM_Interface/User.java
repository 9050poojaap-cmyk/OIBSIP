import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a bank account holder with balance, PIN, and transaction history.
 */
public class User {

    public static final double DAILY_WITHDRAWAL_LIMIT = 25000.0;

    private static final AtomicInteger TRANSACTION_COUNTER = new AtomicInteger(1000);

    private final String userId;
    private String pin;
    private final String name;
    private double balance;
    private final ArrayList<Transaction> transactions;
    private LocalDate withdrawalTrackingDate;
    private double todayWithdrawn;

    /**
     * Creates a new user with an initial balance.
     */
    public User(String userId, String pin, String name, double initialBalance) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty.");
        }
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        this.userId = userId.trim();
        this.pin = pin;
        this.name = name == null ? "Customer" : name.trim();
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.withdrawalTrackingDate = LocalDate.now();
        this.todayWithdrawn = 0.0;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public double getDailyWithdrawalLimit() {
        return DAILY_WITHDRAWAL_LIMIT;
    }

    public double getTodayWithdrawn() {
        resetDailyWithdrawalIfNewDay();
        return todayWithdrawn;
    }

    public double getRemainingDailyLimit() {
        resetDailyWithdrawalIfNewDay();
        return Math.max(DAILY_WITHDRAWAL_LIMIT - todayWithdrawn, 0.0);
    }

    /**
     * Returns an unmodifiable view of transaction history.
     */
    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    public Transaction getLastTransaction() {
        if (transactions.isEmpty()) {
            return null;
        }
        return transactions.get(transactions.size() - 1);
    }

    /**
     * Verifies whether the provided PIN matches the stored PIN.
     */
    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    /**
     * Updates the user's PIN after validation.
     */
    public void changePin(String newPin) {
        if (newPin == null || !newPin.matches("\\d{4}")) {
            throw new IllegalArgumentException("New PIN must be exactly 4 digits.");
        }
        this.pin = newPin;
    }

    /**
     * Credits the account and records a deposit transaction.
     */
    public Transaction deposit(double amount) {
        validatePositiveAmount(amount, "Deposit");
        balance += amount;
        return addTransaction(Transaction.TransactionType.DEPOSIT, amount, "Cash deposit");
    }

    /**
     * Debits the account if sufficient funds exist and within daily limit.
     */
    public Transaction withdraw(double amount) {
        validatePositiveAmount(amount, "Withdrawal");
        resetDailyWithdrawalIfNewDay();

        if (todayWithdrawn + amount > DAILY_WITHDRAWAL_LIMIT) {
            throw new IllegalStateException(String.format(
                    "Daily withdrawal limit exceeded. Limit: Rs.%.2f | Used today: Rs.%.2f | Remaining: Rs.%.2f",
                    DAILY_WITHDRAWAL_LIMIT, todayWithdrawn, getRemainingDailyLimit()));
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient balance. Available: Rs." + String.format("%.2f", balance));
        }
        balance -= amount;
        todayWithdrawn += amount;
        return addTransaction(Transaction.TransactionType.WITHDRAW, amount, "Cash withdrawal");
    }

    /**
     * Credits incoming transfer from another user.
     */
    public Transaction receiveTransfer(double amount, String senderId) {
        validatePositiveAmount(amount, "Transfer");
        balance += amount;
        return addTransaction(Transaction.TransactionType.TRANSFER_IN, amount,
                "Received from User ID: " + senderId);
    }

    /**
     * Debits outgoing transfer to another user.
     */
    public Transaction sendTransfer(double amount, String receiverId) {
        validatePositiveAmount(amount, "Transfer");
        if (amount > balance) {
            throw new IllegalStateException("Insufficient balance for transfer.");
        }
        balance -= amount;
        return addTransaction(Transaction.TransactionType.TRANSFER_OUT, amount,
                "Sent to User ID: " + receiverId);
    }

    private Transaction addTransaction(Transaction.TransactionType type, double amount, String description) {
        String id = "TXN" + TRANSACTION_COUNTER.getAndIncrement();
        Transaction transaction = new Transaction(id, type, amount, description);
        transactions.add(transaction);
        return transaction;
    }

    private void resetDailyWithdrawalIfNewDay() {
        LocalDate today = LocalDate.now();
        if (!today.equals(withdrawalTrackingDate)) {
            withdrawalTrackingDate = today;
            todayWithdrawn = 0.0;
        }
    }

    private void validatePositiveAmount(double amount, String operation) {
        if (amount <= 0) {
            throw new IllegalArgumentException(operation + " amount must be greater than zero.");
        }
    }
}
