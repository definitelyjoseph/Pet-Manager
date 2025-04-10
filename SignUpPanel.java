import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SignUpPanel extends JPanel {
    private CardLayout layout;
    private JPanel parent;
    private List<Customer> customers;

    public SignUpPanel(CardLayout layout, JPanel parent, List<Customer> customers) {
        this.layout = layout;
        this.parent = parent;
        this.customers = customers;

        // Calls the method to display the form when the panel is created
        showForm();
    }

    public void showForm() {
        setLayout(new GridLayout(9, 2));

        // Create fields
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField birthYearField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Add labels and fields to the panel
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Gender:"));
        add(genderField);
        add(new JLabel("Address:"));
        add(addressField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Phone:"));
        add(phoneField);
        add(new JLabel("Birth Year:"));
        add(birthYearField);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);

        // Add a sign-up button to submit the form
        JButton signUpButton = new JButton("Sign Up");
        add(signUpButton);
        signUpButton.addActionListener(e -> {
            String name = nameField.getText();
            String gender = genderField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            int birthYear = Integer.parseInt(birthYearField.getText());
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Save new customer and navigate to the login screen
            Customer newCustomer = new Customer(name, gender, address, email, phone, birthYear, password, username);
            customers.add(newCustomer);
            CustomerStorage.saveCustomers(customers);
            JOptionPane.showMessageDialog(null, "Sign-up successful. Please log in.");
            layout.show(parent, "Login Screen");
        });

        // A back button to return to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        add(backButton);
        backButton.addActionListener(e -> layout.show(parent, "Main Menu"));
    }
}
