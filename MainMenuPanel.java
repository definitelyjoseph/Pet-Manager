import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private JButton loginButton;
    private JButton signUpButton;
    private JButton closeButton;

    public MainMenuPanel(CardLayout layout, JPanel parent) {
        setLayout(new GridLayout(7, 1));

        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        closeButton = new JButton("Close");

        styleButton(loginButton);
        styleButton(signUpButton);
        styleButton(closeButton);

        add(loginButton);
        add(signUpButton);
        add(closeButton);

        loginButton.addActionListener(e -> layout.show(parent, "Login Screen"));

        signUpButton.addActionListener(e -> layout.show(parent, "Sign Up Screen"));

        closeButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }



    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
    }
}
