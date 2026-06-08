import javax.swing.*;
import java.awt.*;

/**
 * Change PIN window with old PIN verification and confirmation matching.
 */
public class ChangePinFrame extends JFrame {

    private final ATM atm;
    private final JPasswordField oldPinField;
    private final JPasswordField newPinField;
    private final JPasswordField confirmPinField;

    public ChangePinFrame(ATM atm) {
        this.atm = atm;

        UIHelper.configureFrame(this, "Change PIN", 460, 400, false);
        setLayout(new BorderLayout());

        add(UIHelper.createHeaderPanel("Change PIN", "Update your 4-digit security PIN"), BorderLayout.NORTH);

        JPanel formPanel = UIHelper.createFormPanel();

        formPanel.add(UIHelper.createFormLabel("Current PIN"), UIHelper.formLabelConstraints(0));
        oldPinField = UIHelper.createPasswordField();
        formPanel.add(oldPinField, UIHelper.formFieldConstraints(0));

        formPanel.add(UIHelper.createFormLabel("New PIN"), UIHelper.formLabelConstraints(1));
        newPinField = UIHelper.createPasswordField();
        formPanel.add(newPinField, UIHelper.formFieldConstraints(1));

        formPanel.add(UIHelper.createFormLabel("Confirm PIN"), UIHelper.formLabelConstraints(2));
        confirmPinField = UIHelper.createPasswordField();
        formPanel.add(confirmPinField, UIHelper.formFieldConstraints(2));

        add(UIHelper.wrapFormContent(formPanel), BorderLayout.CENTER);

        JButton changeButton = UIHelper.createAccentButton("Change PIN");
        JButton cancelButton = UIHelper.createSecondaryButton("Cancel");
        changeButton.addActionListener(e -> handleChangePin());
        cancelButton.addActionListener(e -> dispose());
        add(UIHelper.createButtonBar(changeButton, cancelButton), BorderLayout.SOUTH);
    }

    private void handleChangePin() {
        String oldPin = new String(oldPinField.getPassword()).trim();
        String newPin = new String(newPinField.getPassword()).trim();
        String confirmPin = new String(confirmPinField.getPassword()).trim();

        if (oldPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All PIN fields are required.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            atm.changePin(oldPin, newPin, confirmPin);
            JOptionPane.showMessageDialog(this,
                    "PIN changed successfully. Please use your new PIN next time.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            oldPinField.setText("");
            newPinField.setText("");
            confirmPinField.setText("");
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "PIN Change Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
