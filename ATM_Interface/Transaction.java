import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single financial transaction performed by a user.
 * Immutable record of type, amount, timestamp, and optional description.
 */
public class Transaction {

    /** Supported transaction categories within the ATM system. */
    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER_IN,
        TRANSFER_OUT
    }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String transactionId;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;

    /**
     * Creates a new transaction with the current timestamp.
     *
     * @param transactionId unique identifier for this transaction
     * @param type          transaction category
     * @param amount        monetary amount involved
     * @param description   human-readable detail (e.g. transfer target)
     */
    public Transaction(String transactionId, TransactionType type, double amount, String description) {
        this(transactionId, type, amount, LocalDateTime.now(), description);
    }

    /**
     * Full constructor used when a specific timestamp is required.
     */
    public Transaction(String transactionId, TransactionType type, double amount,
                       LocalDateTime timestamp, String description) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description == null ? "" : description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedDate() {
        return timestamp.format(DATE_FORMATTER);
    }

    public String getFormattedTime() {
        return timestamp.format(TIME_FORMATTER);
    }

    /**
     * Formats the transaction for console display.
     */
    @Override
    public String toString() {
        return String.format(
                "[%s] %s | Amount: Rs.%.2f | %s | %s",
                transactionId,
                type,
                amount,
                timestamp.format(FORMATTER),
                description.isEmpty() ? "-" : description
        );
    }
}
