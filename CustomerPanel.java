import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class CustomerPanel extends JPanel {
    public CustomerPanel(CardLayout layout, JPanel parent, Customer customer, List<Pet> animals, List<AdoptionRequest> requests) {

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Customer Dashboard", SwingConstants.CENTER);

        String[] columns = {"ID", "Name", "Breed", "Gender", "Age"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Wrap animals list in a one-element array to make it final or effectively final
        final List<Pet> animalsRef = animals;

        Runnable refreshTable = () -> {
            model.setRowCount(0);
            for (Pet pet : animalsRef) {
                model.addRow(new Object[]{
                        pet.getId(),
                        pet.getName(),
                        pet.getBreed(),
                        pet.getGender(),
                        pet.getAge()
                });
            }
        };
        refreshTable.run();

        // Side buttons
        JButton requestAdoptionButton = new JButton("Request Adoption");
        JButton cancelAdoptionButton = new JButton("Cancel Adoption");
        JButton viewAdoptionStatusButton = new JButton("Check Adoption Status");
        JButton viewAdoptedPetsButton = new JButton("List Adopted Pets");
        JButton logoutButton = new JButton("Logout");

        JPanel sideButtonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        sideButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sideButtonPanel.add(requestAdoptionButton);
        sideButtonPanel.add(cancelAdoptionButton);
        sideButtonPanel.add(viewAdoptionStatusButton);
        sideButtonPanel.add(viewAdoptedPetsButton);
        sideButtonPanel.add(logoutButton);

        // Sort buttons (smaller)
        JButton sortByAgeButton = new JButton("Sort by Age");
        JButton sortByBreedButton = new JButton("Filter by Breed");
        JButton sortByGenderButton = new JButton("Filter by Gender");
        JButton removeFiltersButton = new JButton("Remove All Filters");

        sortByAgeButton.setPreferredSize(new Dimension(120, 25));
        sortByBreedButton.setPreferredSize(new Dimension(120, 25));

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        sortPanel.add(sortByAgeButton);
        sortPanel.add(sortByBreedButton);
        sortPanel.add(sortByGenderButton);
        sortPanel.add(removeFiltersButton); // Add the Remove Filters button to the panel

        // Add components
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(sideButtonPanel, BorderLayout.EAST);
        add(sortPanel, BorderLayout.SOUTH);

        // Button actions

        requestAdoptionButton.addActionListener(e -> {
            String animalId = JOptionPane.showInputDialog("Enter Animal ID for Adoption:");
            Pet selectedPet = findPetById(animalsRef, animalId);

            if (selectedPet == null) {
                JOptionPane.showMessageDialog(this, "No pet found with that ID.");
            } else if (selectedPet.getAdoptionStat()) {
                JOptionPane.showMessageDialog(this, "This pet is already adopted.");
            } else {
                AdoptionRequest req = customer.requestAdoption(animalId);
                if (req != null) {
                    requests.add(req);
                    AdoptionRequestStorage.saveRequests(requests);
                    JOptionPane.showMessageDialog(this, "Adoption request submitted.");
                }
            }
        });

        cancelAdoptionButton.addActionListener(e -> {
            String animalId = JOptionPane.showInputDialog("Enter Animal ID to Cancel Adoption:");
        
            if (animalId == null || animalId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No ID entered.");
                return;
            }
        
            Pet selectedPet = findPetById(animalsRef, animalId);
        
            if (selectedPet == null) {
                JOptionPane.showMessageDialog(this, "No pet found with that ID.");
            } else if (!selectedPet.getAdoptionStat()) {
                JOptionPane.showMessageDialog(this, "This pet is not adopted.");
            } else {
                boolean success = customer.cancelAdoptionRequest(requests, animalId.trim());
                if (success) {
                    requests.removeIf(req -> req.getAnimalID().equals(animalId.trim()) && req.getCustomerId().equals(customer.getCustomerId()));
                    JOptionPane.showMessageDialog(this, "Adoption request cancelled.");
                } else {
                    JOptionPane.showMessageDialog(this, "No matching adoption request found.");
                }
            }
        });
        
        viewAdoptionStatusButton.addActionListener(e -> customer.viewAdoptionStatus(requests));
        viewAdoptedPetsButton.addActionListener(e -> customer.viewAdoptedPets());
        logoutButton.addActionListener(e -> layout.show(parent, "Main Menu"));

        // Sort actions
        sortByAgeButton.addActionListener(e -> {
            Collections.sort(animalsRef, Comparator.comparingInt(Pet::getAge));
            refreshTable.run();
        });

        sortByBreedButton.addActionListener(e -> {
            String breed = JOptionPane.showInputDialog("Enter Breed to filter by:");
            List<Pet> filteredPets = filterPetsByBreed(animalsRef, breed);
            refreshTableWithFilteredPets(filteredPets, model);
        });

        sortByGenderButton.addActionListener(e -> {
            String gender = JOptionPane.showInputDialog("Enter Gender to filter by (Male/Female):");
            List<Pet> filteredPets = filterPetsByGender(animalsRef, gender);
            refreshTableWithFilteredPets(filteredPets, model);
        });

        // Remove all filters action
        removeFiltersButton.addActionListener(e -> {
            refreshTable.run(); // Refresh the table to show all pets
        });
    }

    private Pet findPetById(List<Pet> animals, String id) {
        for (Pet pet : animals) {
            if (pet.getId().equalsIgnoreCase(id)) {
                return pet;
            }
        }
        return null;
    }

    private List<Pet> filterPetsByBreed(List<Pet> animals, String breed) {
        if (breed == null || breed.isEmpty()) return animals;
        List<Pet> filteredPets = new ArrayList<>();
        for (Pet pet : animals) {
            if (pet.getBreed().equalsIgnoreCase(breed)) {
                filteredPets.add(pet);
            }
        }
        return filteredPets;
    }

    private List<Pet> filterPetsByGender(List<Pet> animals, String gender) {
        if (gender == null || gender.isEmpty()) return animals;
        List<Pet> filteredPets = new ArrayList<>();
        for (Pet pet : animals) {
            if (pet.getGender().equalsIgnoreCase(gender)) {
                filteredPets.add(pet);
            }
        }
        return filteredPets;
    }

    private void refreshTableWithFilteredPets(List<Pet> pets, DefaultTableModel model) {
        model.setRowCount(0);
        for (Pet pet : pets) {
            model.addRow(new Object[]{
                    pet.getId(),
                    pet.getName(),
                    pet.getBreed(),
                    pet.getGender(),
                    pet.getAge()
            });
        }
    }
}

