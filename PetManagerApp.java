import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PetManagerApp} class serves as the main entry point for the pet adoption system application.
 * It initializes the application, sets up the graphical user interface (GUI), and manages the navigation
 * between different panels such as the main menu, login screen, admin dashboard, and customer dashboard.
 * 
 * <p>This class uses Swing components to create an interactive interface and handles the loading and saving
 * of data for pets, customers, and adoption requests.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Initializing the application and loading default data</li>
 *   <li>Setting up the main GUI with a {@code CardLayout} for navigation</li>
 *   <li>Displaying a loading screen during startup</li>
 *   <li>Managing the display and updates of pet data in tables</li>
 * </ul>
 */
public class PetManagerApp {

    private static JFrame frame;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;

    private static List<Pet> animals = PetStorage.loadAnimals();
    private static List<Customer> customers = CustomerStorage.loadCustomers();
    private static ArrayList<AdoptionRequest> requests = AdoptionRequestStorage.loadRequests();
    private static final Admin admin = new Admin("admin", "password");

    /**
     * Constructs a new {@code PetManagerApp} and initializes the application.
     * Sets up the main GUI, loads data, and displays the main menu.
     */
    public PetManagerApp() {
        
        showLoadingScreen();

        frame = new JFrame("Pet Manager System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add all the screens to the CardLayout only once
        MainMenuPanel mainMenuPanel = new MainMenuPanel(cardLayout, mainPanel);
        int nextIdNum = customers.stream()
        .map(Customer::getCustomerId)
        .map(id -> id.replaceAll("[^0-9]", ""))
        .mapToInt(s -> s.isEmpty() ? 0 : Integer.parseInt(s))
        .max()
        .orElse(0) + 1;
    
    String nextCustomerId = String.format("CUST%03d", nextIdNum);
    
    SignUpPanel signUpPanel = new SignUpPanel(cardLayout, mainPanel, customers, nextCustomerId);
    LoginPanel loginPanel = new LoginPanel(cardLayout, mainPanel, customers, animals, requests);
    AdminPanel adminPanel = new AdminPanel(cardLayout, mainPanel, animals, requests, customers, admin);
    CustomerPanel customerPanel = new CustomerPanel(cardLayout, mainPanel, null, animals, requests);
    

        mainPanel.add(mainMenuPanel, "Main Menu");
        mainPanel.add(adminPanel, "Admin Dashboard");
        mainPanel.add(customerPanel, "Customer Dashboard");
        mainPanel.add(loginPanel, "Login Screen");
        mainPanel.add(signUpPanel, "Sign Up Screen");

        frame.add(mainPanel);
        frame.setVisible(true);

        // Adjust the table columns to match the new order
        DefaultTableModel animalTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Breed", "Gender", "Age"}, 0);
        updateTable(animalTableModel);

        cardLayout.show(mainPanel, "Main Menu");
    }

    /**
     * Displays a loading screen with a logo during application startup.
     * The loading screen is shown for 3 seconds before the main GUI is displayed.
     */
    private void showLoadingScreen() {
        JWindow loadingScreen = new JWindow();
    
        // Load the image
        ImageIcon logoIcon = new ImageIcon("Pet_Manager_App_Logo.png");
    
        // Scale the image to fit the loading screen size
        Image scaledImage = logoIcon.getImage().getScaledInstance(900, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
    
        // Create a JLabel to display the scaled image
        JLabel loadingLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        loadingScreen.add(loadingLabel);
    
        // Set the size of the loading screen to 500x400
        loadingScreen.setSize(900, 800);
        loadingScreen.setLocationRelativeTo(null);
        loadingScreen.setVisible(true);
    
        // Wait for 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        // Dispose of the loading screen after the delay
        loadingScreen.dispose();
    }
    

    /**
     * Updates the table model with the current list of pets.
     * Clears the table and repopulates it with pet data.
     *
     * @param model the {@code DefaultTableModel} to be updated
     */
    private void updateTable(DefaultTableModel model) {
        model.setRowCount(0);  // Clear the table first
        for (Pet pet : animals) {
            model.addRow(new Object[]{pet.getId(), pet.getName(), pet.getBreed(), pet.getGender(), pet.getAge()});
        }
    }
    

    /**
     * The main method serves as the entry point for the application.
     * It initializes default data for pets and customers if no data exists,
     * and then launches the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        File petFile = new File("Pets.dat");
        if (!petFile.exists() || PetStorage.loadAnimals().isEmpty()) {
            List<Pet> pets = new ArrayList<>();
            pets.add(new Pet("D_001", "Max", "Pitbull", 2, "Male"));
            pets.add(new Pet("C_001", "Whiskers", "Siamese", 3, "Female"));
            pets.add(new Pet("T_001", "Shelly", "GreenSea", 5, "Female"));
            pets.add(new Pet("B_001", "Bella", "Beagle", 4, "Female"));
            pets.add(new Pet("D_002", "Rocky", "Rottweiler", 3, "Male"));
            pets.add(new Pet("B_002", "Polly", "Parrot", 2, "Female"));
            pets.add(new Pet("F_001", "Finn", "Goldfish", 1, "Male"));
            pets.add(new Pet("H_001", "Harry", "Hamster", 2, "Male"));
            pets.add(new Pet("D_003", "Luna", "Labrador", 3, "Female"));
            pets.add(new Pet("B_003", "Zoe", "ZebraFinch", 1, "Female"));
            pets.add(new Pet("L_001", "Gus", "Gecko", 4, "Male"));
            pets.add(new Pet("B_004", "Kiki", "Cockatiel", 2, "Female"));
            pets.add(new Pet("C_002", "Mittens", "Persian", 2, "Female"));
            pets.add(new Pet("D_004", "Buddy", "Golden Retriever", 3, "Male"));
            pets.add(new Pet("B_005", "Sunny", "Canary", 1, "Male"));
            pets.add(new Pet("F_002", "Bubbles", "Betta", 1, "Female"));
            pets.add(new Pet("G_002", "Nibbles", "Guinea Pig", 2, "Female"));
            pets.add(new Pet("D_005", "Shadow", "German Shepherd", 4, "Male"));
            pets.add(new Pet("C_003", "Snowball", "Maine Coon", 3, "Female"));
            pets.add(new Pet("B_006", "Chirpy", "Lovebird", 2, "Male"));
            pets.add(new Pet("L_002", "Leo", "Chameleon", 3, "Male"));
            pets.add(new Pet("B_007", "Tweety", "Budgerigar", 1, "Female"));
            pets.add(new Pet("D_006", "Daisy", "Dalmatian", 5, "Female"));
            pets.add(new Pet("C_004", "Tiger", "Bengal", 4, "Male"));
            pets.add(new Pet("T_002", "Speedy", "Snapping Turtle", 6, "Male"));
            pets.add(new Pet("B_008", "Peaches", "Cockatoo", 3, "Female"));
            pets.add(new Pet("F_003", "Goldie", "Koi", 2, "Female"));
            pets.add(new Pet("H_003", "Pip", "Dwarf Hamster", 1, "Male"));
            pets.add(new Pet("D_007", "Rex", "Doberman", 4, "Male"));
            pets.add(new Pet("C_005", "Luna", "Russian Blue", 2, "Female"));
            pets.add(new Pet("B_009", "Sky", "Macaw", 5, "Male"));
            pets.add(new Pet("L_003", "Spike", "Iguana", 4, "Male"));
            pets.add(new Pet("B_010", "Coco", "Quaker Parrot", 3, "Female"));
            pets.add(new Pet("D_008", "Bruno", "Boxer", 3, "Male"));
            pets.add(new Pet("C_006", "Mochi", "Scottish Fold", 1, "Female"));
            pets.add(new Pet("T_003", "Tank", "Red-Eared Slider", 7, "Male"));
            pets.add(new Pet("B_011", "Ruby", "Eclectus Parrot", 4, "Female"));
            pets.add(new Pet("F_004", "Splash", "Angelfish", 2, "Male"));
            pets.add(new Pet("H_004", "Fuzzy", "Chinchilla", 3, "Female"));
            pets.add(new Pet("D_009", "Ace", "Husky", 2, "Male"));
            pets.add(new Pet("C_007", "Shadow", "Sphynx", 3, "Male"));
    
            PetStorage.saveAnimals(pets);
            System.out.println("Initial pets saved to Pets.dat");
        }

    File customerFile = new File("customers.dat");
    if (!customerFile.exists() || CustomerStorage.loadCustomers().isEmpty()) {
        List<Customer> defaultCustomers = new ArrayList<>();
        defaultCustomers.add(new Customer("CUST001", "Alice", "Female" , "123 Street", "alice@example.com" , "1234567", 2000, "12345" , "Alice" ));
        defaultCustomers.add(new Customer("CUST002", "Bob", "Male", "456 Avenue", "bob@example.com", "2345678", 1995, "54321", "Bob"));
        defaultCustomers.add(new Customer("CUST003", "Charlie", "Male", "789 Boulevard", "charlie@example.com", "3456789", 2006, "67890", "Charlie"));
        defaultCustomers.add(new Customer("CUST004", "Diana", "Female", "101 Circle", "diana@example.com", "4567890", 1985, "98765", "Diana"));
        defaultCustomers.add(new Customer("CUST005", "Eve", "Female", "202 Square", "eve@example.com", "5678901", 1998, "11223", "Eve"));
        defaultCustomers.add(new Customer("CUST006", "Frank", "Male", "303 Lane", "frank@example.com", "6789012", 1987, "33445", "Frank"));
        defaultCustomers.add(new Customer("CUST007", "Grace", "Female", "404 Road", "grace@example.com", "7890123", 1992, "55667", "Grace"));
        defaultCustomers.add(new Customer("CUST008", "Henry", "Male", "505 Drive", "henry@example.com", "8901234", 1988, "77889", "Henry"));
        defaultCustomers.add(new Customer("CUST009", "Ivy", "Female", "606 Path", "ivy@example.com", "9012345", 2010, "99001", "Ivy"));
        defaultCustomers.add(new Customer("CUST010", "Jack", "Male", "707 Trail", "jack@example.com", "0123456", 1996, "22334", "Jack"));
        defaultCustomers.add(new Customer("CUST011", "Karen", "Female", "808 Way", "karen@example.com", "1234567", 1991, "44556", "Karen"));
        CustomerStorage.saveCustomers(defaultCustomers);
        System.out.println("Initial customers saved to customers.dat");
    }
    
    new PetManagerApp();
}

    /**
     * Styles a button with a consistent appearance, including font, background color, and foreground color.
     *
     * @param button the {@code JButton} to be styled
     */
    private void setButtonStyle(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
    }
}