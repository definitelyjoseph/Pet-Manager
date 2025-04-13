import javax.swing.*;

import java.awt.*;
import java.util.List;


public class LoginPanel extends JPanel {

    public LoginPanel(CardLayout layout, JPanel parent, List<Customer> customers, List<Pet> animals, List<AdoptionRequest> requests) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField userText = new JTextField(15);
        JPasswordField passText = new JPasswordField(15);
        userText.setFont(new Font("Arial", Font.PLAIN, 14));
        passText.setFont(new Font("Arial", Font.PLAIN, 14));

        // Username & Password Panel for alignment
        JPanel fieldPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fieldPanel.add(new JLabel("Username:"));
        fieldPanel.add(userText);
        fieldPanel.add(new JLabel("Password:"));
        fieldPanel.add(createPasswordFieldWithToggle(passText));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(fieldPanel, gbc);

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Go Back");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setPreferredSize(new Dimension(100, 30));
        backButton.setPreferredSize(new Dimension(100, 30));

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(loginButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);

        loginButton.addActionListener(_ -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            if (username.equals("admin") && password.equals("password")) {
                layout.show(parent, "Admin Dashboard");
            } else {
                Customer customer = customers.stream()
                    .filter(c -> c.getUsername().equals(username) && c.getPassword().equals(password))
                    .findFirst().orElse(null);

                if (customer != null) {
                    CustomerPanel customerPanel = new CustomerPanel(layout, parent, customer, animals, requests);
                    parent.add(customerPanel, "Customer Dashboard");
                    layout.show(parent, "Customer Dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login credentials.");
                }
            }
        });

        backButton.addActionListener(e -> layout.show(parent, "Main Menu"));
    }

    private JPanel createPasswordFieldWithToggle(JPasswordField passwordField) {
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
        panel.setPreferredSize(new Dimension(270, 30));
        return panel;
    }
}

