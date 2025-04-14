import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * The {@code CustomerPanel} class represents the graphical user interface (GUI) for the customer dashboard
 * in the pet adoption system. It allows customers to view available pets, request adoptions, cancel adoption requests,
 * view adoption statuses, and sort/filter pets based on specific criteria.
 * 
 * <p>This class extends {@code JPanel} and uses Swing components to create an interactive interface.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Displaying a table of available pets</li>
 *   <li>Submitting and canceling adoption requests</li>
 *   <li>Sorting and filtering pets by age, breed, or gender</li>
 *   <li>Viewing adopted pets and adoption statuses</li>
 * </ul>
 */
public class CustomerPanel extends JPanel {

    /**
     * Constructs a new {@code CustomerPanel} with the specified layout, parent panel, customer, list of pets, and adoption requests.
     *
     * @param layout the {@code CardLayout} used for navigating between panels
     * @param parent the parent {@code JPanel} containing this panel
     * @param customer the {@code Customer} object representing the logged-in customer
     * @param animals the list of available pets
     * @param requests the list of adoption requests
     */
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

    /**
     * Finds a pet by its ID from the given list of pets.
     *
     * @param animals the list of pets to search
     * @param id the ID of the pet to find
     * @return the {@code Pet} object with the specified ID, or {@code null} if no match is found
     */
    private Pet findPetById(List<Pet> animals, String id) {
        for (Pet pet : animals) {
            if (pet.getId().equalsIgnoreCase(id)) {
                return pet;
            }
        }
        return null;
    }

    /**
     * Filters the list of pets by breed.
     *
     * @param animals the list of pets to filter
     * @param breed the breed to filter by
     * @return a list of pets that match the specified breed
     */
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

    /**
     * Filters the list of pets by gender.
     *
     * @param animals the list of pets to filter
     * @param gender the gender to filter by (e.g., "Male" or "Female")
     * @return a list of pets that match the specified gender
     */
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

    /**
     * Refreshes the table to display the given list of pets.
     *
     * @param pets the list of pets to display
     * @param model the {@code DefaultTableModel} used by the table
     */
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

