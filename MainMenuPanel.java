import javax.swing.*;
import java.awt.*;

/**
 * The {@code MainMenuPanel} class represents the main menu screen in the pet adoption system.
 * It provides a graphical user interface (GUI) for users to navigate to the login screen, sign-up screen,
 * or exit the application.
 * 
 * <p>This class extends {@code JPanel} and uses Swing components to create an interactive interface.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Displaying a welcome message and logo</li>
 *   <li>Providing buttons for navigation (Login, Sign Up, Close)</li>
 *   <li>Handling button actions to navigate between screens or exit the application</li>
 * </ul>
 */
public class MainMenuPanel extends JPanel {
    private JButton loginButton;
    private JButton signUpButton;
    private JButton closeButton;
    private JLabel logoLabel;
    private JLabel welcomeLabel;

    /**
     * Constructs a new {@code MainMenuPanel} with the specified layout and parent panel.
     * Initializes the main menu interface with a logo, welcome message, and navigation buttons.
     *
     * @param layout the {@code CardLayout} used for navigating between panels
     * @param parent the parent {@code JPanel} containing this panel
     */
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

    /**
     * Styles a button with a consistent appearance, including font, background color, and foreground color.
     *
     * @param button the {@code JButton} to be styled
     */
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
    }
}
