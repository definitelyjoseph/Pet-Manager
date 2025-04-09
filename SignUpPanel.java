import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SignUpPanel extends JPanel {
    public SignUpPanel(CardLayout layout, JPanel parent, List<Customer> customers) {
        setLayout(new GridLayout(8, 2));

        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField birthYearField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

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

        int option = JOptionPane.showConfirmDialog(null, this, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String gender = genderField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            int birthYear = Integer.parseInt(birthYearField.getText());
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Customer newCustomer = new Customer(name, gender, address, email, phone, birthYear, password, username);
            customers.add(newCustomer);
            CustomerStorage.saveCustomers(customers); // Save the new customer to the file

            JOptionPane.showMessageDialog(null, "Sign-up successful. Please log in.");
            layout.show(parent, "Login Screen");
        } else {
            layout.show(parent, "Main Menu");
        }
    }
}
