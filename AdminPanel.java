import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<Pet> animals;
    private List<AdoptionRequest> requests;
    private List<Customer> customers;
    private Admin admin;
    private List<Pet> allPets;

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
        JButton removeFiltersBtn = new JButton("Remove All Filters");
        filterPanel.add(filterGenderBtn);
        filterPanel.add(filterAdoptedBtn);
        filterPanel.add(filterBreedBtn);
        filterPanel.add(sortAgeBtn);
        filterPanel.add(removeFiltersBtn);
        add(filterPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            JTextField id = new JTextField();
            JTextField name = new JTextField();
            JTextField breed = new JTextField();
            JTextField age = new JTextField();
            JComboBox<String> gender = new JComboBox<>(new String[]{"Male", "Female"});

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("ID:"));
            panel.add(id);
            panel.add(new JLabel("Name:"));
            panel.add(name);
            panel.add(new JLabel("Breed:"));
            panel.add(breed);
            panel.add(new JLabel("Gender:"));
            panel.add(gender);
            panel.add(new JLabel("Age:"));
            panel.add(age);

            if (JOptionPane.showConfirmDialog(this, panel, "Add Pet", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                Pet newPet = new Pet(id.getText(), name.getText(), breed.getText(),
                        Integer.parseInt(age.getText()), (String) gender.getSelectedItem());
                admin.addPet(newPet);
                refreshTable(model, admin.getPets());
            }
        });

        removeBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Pet ID to remove:");
            if (id != null && !id.isEmpty()) {
                admin.removePet(id);
                refreshTable(model, admin.getPets());
            }
        });

        editBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Pet ID to edit:");
            if (id != null && !id.isEmpty()) {
                Pet petToEdit = animals.stream()
                        .filter(p -> p.getId().equals(id))
                        .findFirst().orElse(null);
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
                        String newGender = (String) JOptionPane.showInputDialog(this,
                                "Select new gender:", "Gender", JOptionPane.PLAIN_MESSAGE, null, genders, genders[0]);
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
                        editAllPanel.add(new JLabel("Name:"));
                        editAllPanel.add(nameField);
                        editAllPanel.add(new JLabel("Breed:"));
                        editAllPanel.add(breedField);
                        editAllPanel.add(new JLabel("Gender:"));
                        editAllPanel.add(genderBox);
                        editAllPanel.add(new JLabel("Age:"));
                        editAllPanel.add(ageField);

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
                refreshTable(model, admin.getPets());
            }
        });

        manageRequestsBtn.addActionListener(e -> {
            JDialog requestDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Manage Adoption Requests", true);
            requestDialog.setSize(600, 400);
            requestDialog.setLocationRelativeTo(this);
        
            String[] reqCols = { "Customer ID", "Pet ID", "Status" };
            DefaultTableModel reqModel = new DefaultTableModel(reqCols, 0);
            JTable reqTable = new JTable(reqModel);
            JScrollPane reqScroll = new JScrollPane(reqTable);
        
            Runnable refreshRequests = () -> {
                reqModel.setRowCount(0);
                for (AdoptionRequest r : requests) {
                    reqModel.addRow(new Object[]{
                            r.getCustomerId(), r.getAnimalID(),
                            r.getStatus()
                    });
                }
            };
            refreshRequests.run();
        
            JButton approveBtn = new JButton("Approve");
            JButton denyBtn = new JButton("Deny");
            JButton viewCustomerBtn = new JButton("View Customer Details");
            JButton closeBtn = new JButton("Close");  // Close button
            JPanel actionPanel = new JPanel();
            actionPanel.add(approveBtn);
            actionPanel.add(denyBtn);
            actionPanel.add(viewCustomerBtn);
            actionPanel.add(closeBtn); // Add close button
        
            approveBtn.addActionListener(evt -> {
                int row = reqTable.getSelectedRow();
                if (row != -1) {
                    String id = (String) reqTable.getValueAt(row, 0);
                    AdoptionRequest r = findRequestByCustomerId(requests, id);
                    if (r != null) {
                        r.setStatus("Approved");
                        refreshRequests.run();
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
                        refreshRequests.run();
                        JOptionPane.showMessageDialog(this, "Request Denied!");
                    }
                }
            });
        
            viewCustomerBtn.addActionListener(evt -> {
                int row = reqTable.getSelectedRow();
                if (row != -1) {
                    String customerId = (String) reqTable.getValueAt(row, 0);
                    Customer customer = findCustomerById(customers, customerId);
                    if (customer != null) {
                        // Create a new dialog to show customer details
                        JDialog customerDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Customer Details", true);
                        customerDialog.setSize(300, 200);
                        customerDialog.setLocationRelativeTo(this);
        
                        JPanel customerPanel = new JPanel(new GridLayout(0, 2));
                        customerPanel.add(new JLabel("Customer ID:"));
                        customerPanel.add(new JLabel(customer.getCustomerId()));
                        customerPanel.add(new JLabel("Name:"));
                        customerPanel.add(new JLabel(customer.getName()));
                        customerPanel.add(new JLabel("Email:"));
                        customerPanel.add(new JLabel(customer.getEmail()));
                        customerPanel.add(new JLabel("Address:"));
                        customerPanel.add(new JLabel(customer.getAddress()));
                        customerPanel.add(new JLabel("Gender:"));
                        customerPanel.add(new JLabel(customer.getGender()));
                        customerPanel.add(new JLabel("Phone:"));
                        customerPanel.add(new JLabel(customer.getPhone()));
        
                        // Add close button to the customer dialog
                        JButton closeCustomerDialogBtn = new JButton("Close");
                        closeCustomerDialogBtn.addActionListener(evt2 -> customerDialog.dispose());
                        JPanel closePanel = new JPanel();
                        closePanel.add(closeCustomerDialogBtn);
                        customerDialog.add(customerPanel, BorderLayout.CENTER);
                        customerDialog.add(closePanel, BorderLayout.SOUTH);
                        customerDialog.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "No customer found with ID: " + customerId);
                    }
                }
            });
        
            closeBtn.addActionListener(evt -> requestDialog.dispose());  // Close the request dialog
        
            JPanel bottomPanel = new JPanel();
            bottomPanel.add(approveBtn);
            bottomPanel.add(denyBtn);
            bottomPanel.add(viewCustomerBtn);
            bottomPanel.add(closeBtn);  // Add close button
        
            requestDialog.add(reqScroll, BorderLayout.CENTER);
            requestDialog.add(bottomPanel, BorderLayout.SOUTH);
            requestDialog.setVisible(true);
        
            requestDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        });
        

        logoutBtn.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        sortAgeBtn.addActionListener(e -> {
            animals.sort((a, b) -> Integer.compare(a.getAge(), b.getAge()));
            refreshTable(model, animals);
        });

        filterBreedBtn.addActionListener(e -> {
            String breed = JOptionPane.showInputDialog("Enter Breed to filter by:");
            if (breed != null && !breed.isEmpty()) {
                List<Pet> filtered = allPets.stream()
                        .filter(p -> p.getBreed().equalsIgnoreCase(breed)).collect(Collectors.toList());
                animals.clear();
                animals.addAll(filtered);
                refreshTable(model, animals);
            }
        });

        filterGenderBtn.addActionListener(e -> {
            String gender = JOptionPane.showInputDialog("Enter Gender to filter by (Male/Female):");
            if (gender != null && !gender.isEmpty()) {
                List<Pet> filtered = allPets.stream()
                        .filter(p -> p.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
                animals.clear();
                animals.addAll(filtered);
                refreshTable(model, animals);
            }
        });

        filterAdoptedBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter Adoption Status to filter by (Yes/No):");
            boolean adopted = input != null && input.equalsIgnoreCase("Yes");
            List<Pet> filtered = allPets.stream()
                    .filter(p -> p.getAdoptionStat() == adopted).collect(Collectors.toList());
            animals.clear();
            animals.addAll(filtered);
            refreshTable(model, animals);
        });

        removeFiltersBtn.addActionListener(e -> {
            animals.clear();
            animals.addAll(allPets);
            refreshTable(model, animals);
        });
    }

    private void refreshTable(DefaultTableModel model, List<Pet> pets) {
        model.setRowCount(0);
        for (Pet p : pets) {
            model.addRow(new Object[]{
                    p.getId(), p.getName(), p.getBreed(), p.getGender(),
                    p.getAge(), p.getAdoptionStat() ? "Yes" : "No"
            });
        }
    }

    private AdoptionRequest findRequestByCustomerId(List<AdoptionRequest> requests, String id) {
        for (AdoptionRequest r : requests) {
            if (r.getCustomerId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    private Customer findCustomerById(List<Customer> customers, String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }
    

}
