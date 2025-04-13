import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SignUpPanel extends JPanel {
    private CardLayout layout;
    private JPanel parent;
    private List<Customer> customers;
    private String autoAssignedId;

    public SignUpPanel(CardLayout layout, JPanel parent, List<Customer> customers, String autoAssignedId) {
        this.layout = layout;
        this.parent = parent;
        this.customers = customers;
        this.autoAssignedId = autoAssignedId;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Display Auto-Assigned Customer ID as a Label
        JLabel idLabel = new JLabel("Customer ID: " + autoAssignedId);
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(idLabel, gbc);
        gbc.gridwidth = 1;

        // Create fields
        JTextField nameField = createField();
        JTextField genderField = createField();
        JTextField addressField = createField();
        JTextField emailField = createField();
        JTextField phoneField = createField();
        JTextField birthYearField = createField();
        JTextField usernameField = createField();
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add aligned rows (shifted y index by 1)
        addRow(gbc, 1, "Name:", nameField);
        addRow(gbc, 2, "Gender:", genderField);
        addRow(gbc, 3, "Address:", addressField);
        addRow(gbc, 4, "Email:", emailField);
        addRow(gbc, 5, "Phone:", phoneField);
        addRow(gbc, 6, "Birth Year:", birthYearField);
        addRow(gbc, 7, "Username:", usernameField);
        addRowWithPasswordToggle(gbc, 8, "Password:", passwordField);

        // Buttons
        JButton signUpButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back");
        styleButton(signUpButton);
        styleButton(backButton);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(signUpButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);

        signUpButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String gender = genderField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                int birthYear = Integer.parseInt(birthYearField.getText());
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                Customer newCustomer = new Customer(autoAssignedId, name, gender, address, email, phone, birthYear, password, username);
                customers.add(newCustomer);
                CustomerStorage.saveCustomers(new ArrayList<>(customers));
                JOptionPane.showMessageDialog(this, "Sign-up successful. Please log in.");
                layout.show(parent, "Login Screen");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please ensure all fields are correctly filled.");
            }
        });

        backButton.addActionListener(e -> layout.show(parent, "Main Menu"));
    }

    private JTextField createField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 30));
        return field;
    }

    private void addRow(GridBagConstraints gbc, int y, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }

    private void addRowWithPasswordToggle(GridBagConstraints gbc, int y, String label, JPasswordField passwordField) {
        JButton toggleButton = new JButton("Show");
        toggleButton.setPreferredSize(new Dimension(70, 30));
        toggleButton.setFocusPainted(false);

        final boolean[] visible = {false};
        toggleButton.addActionListener(e -> {
            visible[0] = !visible[0];
            passwordField.setEchoChar(visible[0] ? (char) 0 : '\u2022');
            toggleButton.setText(visible[0] ? "Hide" : "Show");
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(passwordField, BorderLayout.CENTER);
        panel.add(toggleButton, BorderLayout.EAST);
        panel.setPreferredSize(new Dimension(320, 30));

        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(panel, gbc);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(100, 30));
    }
}
