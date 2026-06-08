import java.util.HashMap;
import java.util.Map;

/**
 * Handles user authentication, login attempt tracking, and account locking.
 */
public class AuthenticationManager {

    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private final Map<String, User> users;
    private final Map<String, Integer> failedAttempts;
    private final Map<String, Boolean> lockedAccounts;
    private String lastAuthMessage = "";

    public AuthenticationManager() {
        this.users = new HashMap<>();
        this.failedAttempts = new HashMap<>();
        this.lockedAccounts = new HashMap<>();
        initializePredefinedUsers();
    }

    public String getLastAuthMessage() {
        return lastAuthMessage;
    }

    /**
     * Seeds the system with predefined users for demonstration and transfers.
     */
    private void initializePredefinedUsers() {
        registerUser(new User("U1001", "1234", "Pooja Sharma", 25000.00));
        registerUser(new User("U1002", "5678", "Rahul Verma", 18500.50));
        registerUser(new User("U1003", "9012", "Ananya Patel", 32000.75));
    }

    private void registerUser(User user) {
        users.put(user.getUserId(), user);
        failedAttempts.put(user.getUserId(), 0);
        lockedAccounts.put(user.getUserId(), false);
    }

    /**
     * Looks up a user by ID without authenticating.
     */
    public User findUser(String userId) {
        if (userId == null) {
            return null;
        }
        return users.get(userId.trim());
    }

    /**
     * Checks whether an account is locked due to failed login attempts.
     */
    public boolean isAccountLocked(String userId) {
        return Boolean.TRUE.equals(lockedAccounts.get(userId));
    }

    /**
     * Returns remaining login attempts before lockout.
     */
    public int getRemainingAttempts(String userId) {
        int failed = failedAttempts.getOrDefault(userId, 0);
        return Math.max(0, MAX_LOGIN_ATTEMPTS - failed);
    }

    /**
     * Authenticates a user with User ID and PIN.
     *
     * @return authenticated User on success, null on failure
     */
    public User authenticate(String userId, String pin) {
        if (userId == null || userId.trim().isEmpty()) {
            lastAuthMessage = "Error: User ID cannot be empty.";
            return null;
        }

        String normalizedId = userId.trim();

        if (!users.containsKey(normalizedId)) {
            lastAuthMessage = "Error: User ID not found.";
            return null;
        }

        if (isAccountLocked(normalizedId)) {
            lastAuthMessage = "Account is locked due to multiple failed login attempts. "
                    + "Please contact your bank administrator to unlock the account.";
            return null;
        }

        User user = users.get(normalizedId);

        if (user.verifyPin(pin)) {
            failedAttempts.put(normalizedId, 0);
            lastAuthMessage = "Login successful.";
            return user;
        }

        int attempts = failedAttempts.getOrDefault(normalizedId, 0) + 1;
        failedAttempts.put(normalizedId, attempts);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            lockedAccounts.put(normalizedId, true);
            lastAuthMessage = "Account locked after " + MAX_LOGIN_ATTEMPTS + " failed attempts.";
        } else {
            int remaining = MAX_LOGIN_ATTEMPTS - attempts;
            lastAuthMessage = "Invalid PIN. Attempts remaining: " + remaining;
        }

        return null;
    }

    /**
     * Admin utility to unlock an account (for demonstration purposes).
     */
    public void unlockAccount(String userId) {
        if (users.containsKey(userId)) {
            lockedAccounts.put(userId, false);
            failedAttempts.put(userId, 0);
        }
    }
}
