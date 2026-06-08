/**
 * Core banking service layer orchestrating authentication and financial operations.
 * Business logic is shared by the Swing GUI presentation layer.
 */
public class ATM {

    private final AuthenticationManager authManager;
    private User currentUser;

    public ATM() {
        this.authManager = new AuthenticationManager();
    }

    public AuthenticationManager getAuthManager() {
        return authManager;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void logout() {
        currentUser = null;
    }

    /**
     * Parses and validates a positive monetary amount from user input.
     */
    public static double parsePositiveAmount(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Amount cannot be empty.");
        }
        try {
            double amount = Double.parseDouble(input.trim());
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            return amount;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid amount. Please enter a valid number.");
        }
    }

    private User requireCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        return currentUser;
    }

    public Transaction deposit(double amount) {
        return deposit(requireCurrentUser(), amount);
    }

    public Transaction deposit(User user, double amount) {
        return user.deposit(amount);
    }

    public Transaction withdraw(double amount) {
        return withdraw(requireCurrentUser(), amount);
    }

    public Transaction withdraw(User user, double amount) {
        return user.withdraw(amount);
    }

    public TransferResult transfer(String receiverId, double amount) {
        return transfer(requireCurrentUser(), receiverId, amount);
    }

    public TransferResult transfer(User sender, String receiverId, double amount) {
        if (receiverId == null || receiverId.trim().isEmpty()) {
            throw new IllegalArgumentException("Receiver User ID cannot be empty.");
        }
        String normalizedReceiverId = receiverId.trim();
        if (normalizedReceiverId.equals(sender.getUserId())) {
            throw new IllegalArgumentException("Cannot transfer money to your own account.");
        }
        User receiver = authManager.findUser(normalizedReceiverId);
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver account does not exist.");
        }
        Transaction sent = sender.sendTransfer(amount, normalizedReceiverId);
        receiver.receiveTransfer(amount, sender.getUserId());
        return new TransferResult(sent, receiver);
    }

    public void changePin(String oldPin, String newPin, String confirmPin) {
        changePin(requireCurrentUser(), oldPin, newPin, confirmPin);
    }

    public void changePin(User user, String oldPin, String newPin, String confirmPin) {
        if (!user.verifyPin(oldPin)) {
            throw new IllegalArgumentException("Incorrect current PIN.");
        }
        if (!newPin.equals(confirmPin)) {
            throw new IllegalArgumentException("New PIN and confirmation do not match.");
        }
        if (newPin.equals(oldPin)) {
            throw new IllegalArgumentException("New PIN must be different from the current PIN.");
        }
        user.changePin(newPin);
    }

    public static class TransferResult {
        private final Transaction transaction;
        private final User receiver;

        public TransferResult(Transaction transaction, User receiver) {
            this.transaction = transaction;
            this.receiver = receiver;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public User getReceiver() {
            return receiver;
        }
    }
}
