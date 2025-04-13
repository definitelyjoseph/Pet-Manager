import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private JButton loginButton;
    private JButton signUpButton;
    private JButton closeButton;
    private JLabel logoLabel;
    private JLabel welcomeLabel;

    public MainMenuPanel(CardLayout layout, JPanel parent) {
        setLayout(new BorderLayout());

        // Load and scale the logo image
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\amoya\\Downloads\\New folder (2)\\ChatGPT Image Apr 12, 2025, 12_51_25 AM.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH); // Resize logo
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        // Create the logo label and add it to the top
        logoLabel = new JLabel(scaledIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create the welcome text label
        welcomeLabel = new JLabel("Welcome to Pet Manager!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));

        // Panel to hold logo and welcome text
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(logoLabel, BorderLayout.NORTH);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Create the buttons panel and align it to the bottom
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        closeButton = new JButton("Close");

        styleButton(loginButton);
        styleButton(signUpButton);
        styleButton(closeButton);

        buttonsPanel.add(loginButton);
        buttonsPanel.add(signUpButton);
        buttonsPanel.add(closeButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Button actions
        loginButton.addActionListener(e -> layout.show(parent, "Login Screen"));
        signUpButton.addActionListener(e -> layout.show(parent, "Sign Up Screen"));
        closeButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
    }
}
