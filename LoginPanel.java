import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginPanel extends JPanel {
    public LoginPanel(CardLayout layout, JPanel parent, List<Customer> customers) {
        setLayout(new GridLayout(5, 2));

        JTextField userText = new JTextField(10);
        JPasswordField passText = new JPasswordField(10);
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Go Back");

        add(new JLabel("Username:"));
        add(userText);
        add(new JLabel("Password:"));
        add(passText);
        add(loginButton);
        add(backButton);

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
                    layout.show(parent, "Customer Dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login credentials.");
                }
            }
        });

        backButton.addActionListener(e -> layout.show(parent, "Main Menu"));
    }
}
