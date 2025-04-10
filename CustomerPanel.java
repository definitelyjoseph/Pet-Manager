import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {
    public CustomerPanel(CardLayout layout, JPanel parent, Customer customer, List<Animal> animals, List<AdoptionRequest> requests) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Customer Dashboard", SwingConstants.CENTER);

        String[] columns = {"ID", "Name", "Breed", "Age"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        for (Animal pets : animals) {
            model.addRow(new Object[]{Pet.getId(), Pet.getName(), Pet.getBreed(), Pet.getAge()});
        }

        JButton viewAnimalsButton = new JButton("View Animals");
        JButton requestAdoptionButton = new JButton("Request Adoption");
        JButton viewAdoptionStatusButton = new JButton("View Adoption Status");
        JButton viewAdoptedPetsButton = new JButton("View Adopted Pets");
        JButton logoutButton = new JButton("Logout");

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        buttonPanel.add(viewAnimalsButton);
        buttonPanel.add(requestAdoptionButton);
        buttonPanel.add(viewAdoptionStatusButton);
        buttonPanel.add(viewAdoptedPetsButton);
        buttonPanel.add(logoutButton);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        viewAnimalsButton.addActionListener(e -> customer.viewPets(animals));

        requestAdoptionButton.addActionListener(e -> {
            String animalId = JOptionPane.showInputDialog("Enter Animal ID for Adoption:");
            AdoptionRequest req = customer.requestAdoption(aniId);
            if (req != null) {
                requests.add(req);
                AdoptionRequestStorage.saveRequests(requests);
            }
        });

        viewAdoptionStatusButton.addActionListener(e -> customer.viewAdoptionStatus(requests));
        viewAdoptedPetsButton.addActionListener(e -> customer.viewAdoptedPets());
        logoutButton.addActionListener(e -> layout.show(parent, "Main Menu"));
    }
}

