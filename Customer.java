import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Customer implements Serializable {
    private String customerId, name, gender, address, email, phone, password, username;
    private int birthYear;
    private List<String> adoptedPets;
    private static final long serialVersionUID = 1L;  // Add this line

    public Customer(String customerId, String name, String gender, String address, String email, String phone, int birthYear, String password, String username) {
        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.birthYear = birthYear;
        this.password = password;
        this.username = username;
        this.adoptedPets = new ArrayList<>();
    }

    public boolean isEligibleToAdopt() {
        int currentYear = Year.now().getValue();
        return (currentYear - birthYear) >= 21;
    }

    public AdoptionRequest requestAdoption(String animalID) {
        if (!isEligibleToAdopt()) {
            JOptionPane.showMessageDialog(null, "You must be at least 21 years old to adopt a pet.");
            return null;
        }

        if (animalID == null || animalID.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid animal ID.");
            return null;
        }

        JOptionPane.showMessageDialog(null, "Pet adoption request submitted successfully!");
        return new AdoptionRequest(customerId, animalID.trim());
    }

    public boolean cancelAdoptionRequest(List<AdoptionRequest> requests, String animalId) {
        if (animalId == null || animalId.trim().isEmpty()) {
            return false;
        }

        for (AdoptionRequest req : requests) {
            if (req.getAnimalID().equals(animalId.trim()) && req.getCustomerId().equals(this.customerId)) {
                return true;
            }
        }

        return false;
    }

    public void viewAdoptionStatus(List<AdoptionRequest> requests) {
        boolean found = false;
        StringBuilder statusMessage = new StringBuilder();

        for (AdoptionRequest req : requests) {
            if (req.getCustomerId().equals(this.customerId)) {
                if (!found) {
                    statusMessage.append(req.toString());  // Add status message for the first matching request
                    found = true;
                }
            }
        }

        if (found) {
            JOptionPane.showMessageDialog(null, statusMessage.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No adoption status found for this customer.");
        }
    }

    public void viewPets(List<Pet> animals) {
        StringBuilder sb = new StringBuilder("Available Pets:\n\n");
        for (Pet animal : animals) {
            sb.append(animal).append("\n");
        }
        showMessage(sb.toString(), "Available Pets");
    }

    public void viewAdoptedPets() {
        adoptedPets.clear();
        List<AdoptionRequest> requests = AdoptionRequestStorage.loadRequests();
        List<Pet> allAnimals = PetStorage.loadAnimals();

        StringBuilder sb = new StringBuilder("Adopted Pets for " + customerId + ":\n\n");
        boolean hasAdopted = false;

        for (AdoptionRequest request : requests) {
            if (request.getCustomerId().equals(customerId) && request.getStatus().equals("Approved")) {
                for (Pet pet : allAnimals) {
                    if (pet.getId().equals(request.getAnimalID())) {
                        adoptedPets.add(pet.getId());
                        sb.append("ID: ").append(pet.getId()).append("\n");
                        sb.append("Name: ").append(pet.getName()).append("\n");
                        sb.append("Breed: ").append(pet.getBreed()).append("\n");
                        sb.append("Age: ").append(pet.getAge()).append("\n");
                        sb.append("Gender: ").append(pet.getGender()).append("\n");
                        sb.append("-----------------------\n");
                        hasAdopted = true;
                    }
                }
            }
        }

        if (!hasAdopted) {
            sb.append("No pets adopted yet.");
        }

        showMessage(sb.toString(), "Adopted Pets");
    }

    private void showMessage(String message, String title) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void addAdoptedPet(String animalID) {
        adoptedPets.add(animalID);
    }

    // Getter methods
    public String getCustomerId() { return customerId; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public int getBirthYear() { return birthYear; }
}
