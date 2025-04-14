import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code AdminPanel} class represents the graphical user interface (GUI) for the admin dashboard
 * in the pet adoption system. It allows the admin to manage pets, adoption requests, and perform various
 * administrative tasks such as adding, editing, and removing pets, as well as approving or denying adoption requests.
 * 
 * <p>This class extends {@code JPanel} and uses Swing components to create an interactive interface.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Displaying a table of pets with their details</li>
 *   <li>Adding, editing, and removing pets</li>
 *   <li>Managing adoption requests (approve, deny, or remove requests)</li>
 *   <li>Sorting and filtering pets by various criteria</li>
 *   <li>Navigating back to the main menu</li>
 * </ul>
 */
public class AdminPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<Pet> animals;
    private List<AdoptionRequest> requests;
    private List<Customer> customers;
    private Admin admin;
    private List<Pet> allPets;

    /**
     * Constructs a new {@code AdminPanel} with the specified layout, parent panel, and data lists.
     * Initializes the admin dashboard interface with tables, buttons, and filters.
     *
     * @param cardLayout the {@code CardLayout} used for navigating between panels
     * @param mainPanel the parent {@code JPanel} containing this panel
     * @param animals the list of pets
     * @param requests the list of adoption requests
     * @param customers the list of customers
     * @param admin the {@code Admin} object representing the admin user
     */
    public AdminPanel(CardLayout cardLayout, JPanel mainPanel, List<Pet> animals,
                      List<AdoptionRequest> requests, List<Customer> customers, Admin admin) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.animals = animals;
        this.requests = requests;
        this.customers = customers;
        this.admin = admin;
        this.allPets = animals.stream().collect(Collectors.toList());

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setPreferredSize(new Dimension(0, 40));
        title.setForeground(Color.DARK_GRAY);
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Breed", "Gender", "Age", "Adopted"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable(model, animals);

        JPanel buttons = new JPanel(new GridLayout(0, 1, 10, 10));
        buttons.setBackground(Color.WHITE);
        JButton addBtn = new JButton("Add Pet");
        JButton removeBtn = new JButton("Remove Pet");
        JButton editBtn = new JButton("Edit Pet");
        JButton manageRequestsBtn = new JButton("Manage Requests");
        JButton logoutBtn = new JButton("Logout");
        buttons.add(addBtn);
        buttons.add(removeBtn);
        buttons.add(editBtn);
        buttons.add(manageRequestsBtn);
        buttons.add(logoutBtn);
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(buttons, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        JButton filterGenderBtn = new JButton("Filter by Gender");
        JButton filterAdoptedBtn = new JButton("Filter by Adopted");
        JButton filterBreedBtn = new JButton("Filter by Breed");
        JButton sortAgeBtn = new JButton("Sort by Age");
        JButton sortIdBtn = new JButton("Sort by ID");
        JButton removeFiltersBtn = new JButton("Remove All Filters");
        filterPanel.add(filterGenderBtn);
        filterPanel.add(filterAdoptedBtn);
        filterPanel.add(filterBreedBtn);
        filterPanel.add(sortAgeBtn);
        filterPanel.add(sortIdBtn);
        filterPanel.add(removeFiltersBtn);
        add(filterPanel, BorderLayout.SOUTH);

        // Add Pet
        addBtn.addActionListener(e -> handleAddPetAction());

        // Remove Pet
        removeBtn.addActionListener(e -> handleRemovePetAction());

        // Edit Pet
        editBtn.addActionListener(e -> handleEditPetAction());

        // Manage Requests
        manageRequestsBtn.addActionListener(e -> handleManageRequestsAction());

        // Logout
        logoutBtn.addActionListener(e -> handleLogoutAction());

        // Sorting and Filtering
        sortAgeBtn.addActionListener(e -> handleSortByAgeAction());

        sortIdBtn.addActionListener(e -> handleSortByIdAction());

        filterBreedBtn.addActionListener(e -> handleFilterByBreedAction());

        filterGenderBtn.addActionListener(e -> handleFilterByGenderAction());

        filterAdoptedBtn.addActionListener(e -> handleFilterByAdoptionStatusAction());

        removeFiltersBtn.addActionListener(e -> handleRemoveAllFiltersAction());
    }

    /**
     * Refreshes the table to display the given list of pets.
     *
     * @param model the {@code DefaultTableModel} used by the table
     * @param pets the list of pets to display
     */
    private void refreshTable(DefaultTableModel model, List<Pet> pets) {
        model.setRowCount(0);
        for (Pet p : pets) {
            model.addRow(new Object[]{
                    p.getId(), p.getName(), p.getBreed(), p.getGender(),
                    p.getAge(), p.getAdoptionStat() ? "Yes" : "No"
            });
        }
    }

    /**
     * Finds an adoption request by the customer's ID.
     *
     * @param requests the list of adoption requests
     * @param id the ID of the customer
     * @return the {@code AdoptionRequest} object if found, or {@code null} if not found
     */
    private AdoptionRequest findRequestByCustomerId(List<AdoptionRequest> requests, String id) {
        for (AdoptionRequest r : requests) {
            if (r.getCustomerId().equals(id)) return r;
        }
        return null;
    }

    /**
     * Finds a customer by their unique ID.
     *
     * @param customers the list of customers
     * @param customerId the unique ID of the customer
     * @return the {@code Customer} object if found, or {@code null} if not found
     */
    private Customer findCustomerById(List<Customer> customers, String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(customerId)) return c;
        }
        return null;
    }

    /**
     * Handles the action for adding a new pet.
     * Displays a dialog for entering pet details and adds the pet to the list if confirmed.
     */
    private void handleAddPetAction() {
        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField breed = new JTextField();
        JTextField age = new JTextField();
        JComboBox<String> gender = new JComboBox<>(new String[]{"Male", "Female"});

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("ID:")); panel.add(id);
        panel.add(new JLabel("Name:")); panel.add(name);
        panel.add(new JLabel("Breed:")); panel.add(breed);
        panel.add(new JLabel("Gender:")); panel.add(gender);
        panel.add(new JLabel("Age:")); panel.add(age);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Pet", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Pet newPet = new Pet(id.getText(), name.getText(), breed.getText(),
                    Integer.parseInt(age.getText()), (String) gender.getSelectedItem());
            PetStorage.addAnimal(newPet);
            admin.getPets().add(newPet);
            refreshTable(model, admin.getPets());
        }
    }

    /**
     * Handles the action for removing a pet.
     * Prompts the admin to enter the pet ID and removes the pet if it exists.
     */
    private void handleRemovePetAction() {
        String id = JOptionPane.showInputDialog("Enter Pet ID to remove:");
        if (id != null && !id.isEmpty()) {
            admin.removePet(id);
            PetStorage.saveAnimals(admin.getPets());
            refreshTable(model, admin.getPets());
        }
    }

    /**
     * Handles the action for editing a pet's details.
     * Prompts the admin to select a pet and edit its details.
     */
    private void handleEditPetAction() {
        String id = JOptionPane.showInputDialog("Enter Pet ID to edit:");
        if (id != null && !id.isEmpty()) {
            Pet petToEdit = animals.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
            if (petToEdit == null) {
                JOptionPane.showMessageDialog(this, "No pet found with ID: " + id);
                return;
            }

            String[] options = {"Name", "Breed", "Gender", "Age", "Edit All"};
            String choice = (String) JOptionPane.showInputDialog(this, "Select field to edit:",
                    "Edit Options", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choice == null) return;

            Pet updated = new Pet(petToEdit.getId(), petToEdit.getName(),
                    petToEdit.getBreed(), petToEdit.getAge(), petToEdit.getGender());

            switch (choice) {
                case "Name" -> {
                    String newName = JOptionPane.showInputDialog("Enter new name:");
                    if (newName != null) updated.setName(newName);
                }
                case "Breed" -> {
                    String newBreed = JOptionPane.showInputDialog("Enter new breed:");
                    if (newBreed != null) updated.setBreed(newBreed);
                }
                case "Gender" -> {
                    String[] genders = {"Male", "Female"};
                    String newGender = (String) JOptionPane.showInputDialog(this, "Select new gender:",
                            "Gender", JOptionPane.PLAIN_MESSAGE, null, genders, genders[0]);
                    if (newGender != null) updated.setGender(newGender);
                }
                case "Age" -> {
                    String newAge = JOptionPane.showInputDialog("Enter new age:");
                    if (newAge != null) {
                        try {
                            updated.setAge(Integer.parseInt(newAge));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Invalid age entered.");
                            return;
                        }
                    }
                }
                case "Edit All" -> {
                    JTextField nameField = new JTextField(petToEdit.getName());
                    JTextField breedField = new JTextField(petToEdit.getBreed());
                    JTextField ageField = new JTextField(String.valueOf(petToEdit.getAge()));
                    JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female"});
                    genderBox.setSelectedItem(petToEdit.getGender());

                    JPanel editAllPanel = new JPanel(new GridLayout(4, 2));
                    editAllPanel.add(new JLabel("Name:")); editAllPanel.add(nameField);
                    editAllPanel.add(new JLabel("Breed:")); editAllPanel.add(breedField);
                    editAllPanel.add(new JLabel("Gender:")); editAllPanel.add(genderBox);
                    editAllPanel.add(new JLabel("Age:")); editAllPanel.add(ageField);

                    int result = JOptionPane.showConfirmDialog(this, editAllPanel, "Edit Pet Details", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        updated.setName(nameField.getText());
                        updated.setBreed(breedField.getText());
                        updated.setGender((String) genderBox.getSelectedItem());
                        try {
                            updated.setAge(Integer.parseInt(ageField.getText()));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Invalid age entered.");
                            return;
                        }
                    } else return;
                }
            }

            admin.editPet(id, updated);
            PetStorage.saveAnimals(admin.getPets());
            refreshTable(model, admin.getPets());
        }
    }

    /**
     * Handles the action for managing adoption requests.
     * Displays a dialog for viewing, approving, denying, or removing adoption requests.
     */
    private void handleManageRequestsAction() {
        JDialog requestDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Manage Adoption Requests", true);
        requestDialog.setSize(600, 400);
        requestDialog.setLocationRelativeTo(this);

        String[] reqCols = {"Customer ID", "Pet ID", "Status"};
        DefaultTableModel reqModel = new DefaultTableModel(reqCols, 0);
        JTable reqTable = new JTable(reqModel);
        JScrollPane reqScroll = new JScrollPane(reqTable);

        Runnable refreshRequests = () -> {
            reqModel.setRowCount(0);
            for (AdoptionRequest r : requests) {
                reqModel.addRow(new Object[]{r.getCustomerId(), r.getAnimalID(), r.getStatus()});
            }
        };
        refreshRequests.run();

        JButton approveBtn = new JButton("Approve");
        JButton denyBtn = new JButton("Deny");
        JButton viewCustomerBtn = new JButton("View Request Details");
        JButton removerequestBtn = new JButton("Remove");
        JButton closeBtn = new JButton("Close");

        approveBtn.addActionListener(evt -> {
            int row = reqTable.getSelectedRow();
            if (row != -1) {
                String id = (String) reqTable.getValueAt(row, 0);
                AdoptionRequest r = findRequestByCustomerId(requests, id);
                if (r != null) {
                    r.setStatus("Approved");
                    
                    // Find the pet corresponding to the adoption request and update its status
                    Pet petToUpdate = animals.stream().filter(p -> p.getId().equals(r.getAnimalID())).findFirst().orElse(null);
                    if (petToUpdate != null) {
                        petToUpdate.setAdoptionStat(true); // Set adoption status to "Yes"
                    }
        
                    AdoptionRequestStorage.saveRequests(requests);
                    PetStorage.saveAnimals(animals); // Save the updated pets list with new adoption status
                    refreshRequests.run();
                    refreshTable(model, animals); // Refresh both the request and pet tables
                    JOptionPane.showMessageDialog(this, "Request Approved!");
                }
            }
        });

        denyBtn.addActionListener(evt -> {
            int row = reqTable.getSelectedRow();
            if (row != -1) {
                String id = (String) reqTable.getValueAt(row, 0);
                AdoptionRequest r = findRequestByCustomerId(requests, id);
                if (r != null) {
                    r.setStatus("Denied");
                    AdoptionRequestStorage.saveRequests(requests); 
                    refreshRequests.run();
                    JOptionPane.showMessageDialog(this, "Request Denied!");
                }
            }
        });

        viewCustomerBtn.addActionListener(evt -> {
            int row = reqTable.getSelectedRow();
            if (row != -1) {
                String customerId = (String) reqTable.getValueAt(row, 0);
                String petId = (String) reqTable.getValueAt(row, 1);
                Customer customer = findCustomerById(customers, customerId);
                Pet pet = animals.stream().filter(p -> p.getId().equals(petId)).findFirst().orElse(null);

                if (customer != null) {
                    JDialog customerDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Customer & Pet Details", true);
                    customerDialog.setSize(350, 300);
                    customerDialog.setLocationRelativeTo(this);

                    JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
                    infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    infoPanel.add(new JLabel("Customer ID:")); infoPanel.add(new JLabel(customer.getCustomerId()));
                    infoPanel.add(new JLabel("Name:")); infoPanel.add(new JLabel(customer.getName()));
                    infoPanel.add(new JLabel("Email:")); infoPanel.add(new JLabel(customer.getEmail()));
                    infoPanel.add(new JLabel("Address:")); infoPanel.add(new JLabel(customer.getAddress()));
                    infoPanel.add(new JLabel("Gender:")); infoPanel.add(new JLabel(customer.getGender()));
                    infoPanel.add(new JLabel("Phone:")); infoPanel.add(new JLabel(customer.getPhone()));
                    infoPanel.add(new JLabel("")); infoPanel.add(new JLabel(""));

                    if (pet != null) {
                        infoPanel.add(new JLabel("Pet ID:")); infoPanel.add(new JLabel(pet.getId()));
                        infoPanel.add(new JLabel("Name:")); infoPanel.add(new JLabel(pet.getName()));
                        infoPanel.add(new JLabel("Breed:")); infoPanel.add(new JLabel(pet.getBreed()));
                        infoPanel.add(new JLabel("Gender:")); infoPanel.add(new JLabel(pet.getGender()));
                        infoPanel.add(new JLabel("Age:")); infoPanel.add(new JLabel(String.valueOf(pet.getAge())));
                    }

                    JButton customerCloseBtn = new JButton("Close");
                    customerCloseBtn.addActionListener(e2 -> customerDialog.dispose());
                    JPanel closePanel = new JPanel();
                    closePanel.add(customerCloseBtn);

                    customerDialog.add(infoPanel, BorderLayout.CENTER);
                    customerDialog.add(closePanel, BorderLayout.SOUTH);
                    customerDialog.setVisible(true);
                }
            }
        });

        removerequestBtn.addActionListener(evt -> {
            int row = reqTable.getSelectedRow();
            if (row != -1) {
                String id = (String) reqTable.getValueAt(row, 0);
                AdoptionRequest r = findRequestByCustomerId(requests, id);
                if (r != null) {
                    if (r.getStatus().equals("Pending")) {
                        JOptionPane.showMessageDialog(this, "You must approve or deny the request before removing it.");
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(this, 
                            "Are you sure you want to delete this request?", 
                            "Confirm Deletion", 
                            JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            requests.remove(r);
                            AdoptionRequestStorage.saveRequests(requests);
                            refreshRequests.run();
                            JOptionPane.showMessageDialog(this, "Request removed.");
                        }
                    }
                }
            }
        });

        closeBtn.addActionListener(evt -> requestDialog.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(approveBtn);
        bottomPanel.add(denyBtn);
        bottomPanel.add(viewCustomerBtn);
        bottomPanel.add(removerequestBtn);
        bottomPanel.add(closeBtn);

        requestDialog.add(reqScroll, BorderLayout.CENTER);
        requestDialog.add(bottomPanel, BorderLayout.SOUTH);
        requestDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        requestDialog.setVisible(true);
    }

    /**
     * Handles the action for sorting pets by age.
     * Sorts the list of pets in ascending order of age and refreshes the table.
     */
    private void handleSortByAgeAction() {
        animals.sort((a, b) -> Integer.compare(a.getAge(), b.getAge()));
        refreshTable(model, animals);
    }

    /**
     * Handles the action for sorting pets by ID.
     * Sorts the list of pets in ascending order of ID and refreshes the table.
     */
    private void handleSortByIdAction() {
        animals.sort((a, b) -> a.getId().compareToIgnoreCase(b.getId()));
        refreshTable(model, animals);
    }

    /**
     * Handles the action for filtering pets by breed.
     * Prompts the admin to enter a breed and filters the list of pets accordingly.
     */
    private void handleFilterByBreedAction() {
        String breed = JOptionPane.showInputDialog("Enter Breed to filter by:");
        if (breed != null && !breed.isEmpty()) {
            List<Pet> filtered = allPets.stream().filter(p -> p.getBreed().equalsIgnoreCase(breed)).collect(Collectors.toList());
            animals.clear(); animals.addAll(filtered);
            refreshTable(model, animals);
        }
    }

    /**
     * Handles the action for filtering pets by gender.
     * Prompts the admin to enter a gender and filters the list of pets accordingly.
     */
    private void handleFilterByGenderAction() {
        String gender = JOptionPane.showInputDialog("Enter Gender to filter by (Male/Female):");
        if (gender != null && !gender.isEmpty()) {
            List<Pet> filtered = allPets.stream().filter(p -> p.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
            animals.clear(); animals.addAll(filtered);
            refreshTable(model, animals);
        }
    }

    /**
     * Handles the action for filtering pets by adoption status.
     * Prompts the admin to enter an adoption status (Yes/No) and filters the list of pets accordingly.
     */
    private void handleFilterByAdoptionStatusAction() {
        String input = JOptionPane.showInputDialog("Enter Adoption Status to filter by (Yes/No):");
        boolean adopted = input != null && input.equalsIgnoreCase("Yes");
        List<Pet> filtered = allPets.stream().filter(p -> p.getAdoptionStat() == adopted).collect(Collectors.toList());
        animals.clear(); animals.addAll(filtered);
        refreshTable(model, animals);
    }

    /**
     * Handles the action for removing all filters.
     * Resets the list of pets to the original list and refreshes the table.
     */
    private void handleRemoveAllFiltersAction() {
        animals.clear();
        animals.addAll(allPets);
        refreshTable(model, animals);
    }

    /**
     * Handles the action for logging out.
     * Navigates back to the main menu.
     */
    private void handleLogoutAction() {
        cardLayout.show(mainPanel, "Main Menu");
    }
}
