import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;



   
    public class PetManagerApp {
        
        private void setButtonStyle(JButton button) {
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            button.setBackground(Color.LIGHT_GRAY);
            button.setForeground(Color.BLACK);
    }
        private static JFrame frame;
        private static JPanel mainPanel;
        private static CardLayout cardLayout;
    
        private static List<Animal> pets = AnimalStorage.loadAnimals();
        private static List<Customer> customers = CustomerStorage.loadCustomers();
        private static List<AdoptionRequest> requests = AdoptionRequestStorage.loadRequests();
    
        public PetManagerApp() {
            showLoadingScreen();
    
            frame = new JFrame("Pet Manager System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout());
    
            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
    
            MainMenuPanel mainMenuPanel = new MainMenuPanel(cardLayout, mainPanel);
            SignUpPanel signUpPanel = new SignUpPanel(cardLayout, mainPanel, customers);
            LoginPanel loginPanel = new LoginPanel(cardLayout, mainPanel, customers);
            CustomerPanel customerPanel = new CustomerPanel(cardLayout, mainPanel, null, animals, requests);

            mainPanel.add(mainMenuPanel, "Main Menu");
            mainPanel.add(customerPanel, "Customer Dashboard");
            mainPanel.add(loginPanel, "Login Screen");
            mainPanel.add(signUpPanel, "Sign Up Screen");

           
            frame.add(mainPanel);
            frame.setVisible(true);

            cardLayout.show(mainPanel, "Main Menu");

        }
    
        private void showLoadingScreen() {
            JWindow loadingScreen = new JWindow();
            JLabel loadingLabel = new JLabel("Welcome to Pet Manager", SwingConstants.CENTER);
            loadingLabel.setFont(new Font("Arial", Font.BOLD, 20));
            loadingScreen.add(loadingLabel);
            loadingScreen.setSize(500, 400);
            loadingScreen.setLocationRelativeTo(null);
            loadingScreen.setVisible(true);
    
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadingScreen.dispose();
        }
    
        private void updateTable(DefaultTableModel model) {
            model.setRowCount(0);
            for (Pets pets : animals) {
                model.addRow(new Object[]{pets.getId(), pets.getName(), pets.getBreed(), pets.getAge()});
            }
        }

        public static void main(String[] args) {
            new PetManagerApp();
        }
    }
    