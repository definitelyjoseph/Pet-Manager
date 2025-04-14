import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
/**
 * The {@code Customer} class represents a customer in the pet adoption system.
 * It stores customer details such as personal information, contact details, and adoption-related data.
 * This class also provides methods for managing adoption requests, viewing adoption statuses, and interacting with pets.
 * 
 * <p>Implements {@code Serializable} to allow instances of this class to be serialized for storage or transmission.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Checking eligibility for adoption</li>
 *   <li>Submitting and canceling adoption requests</li>
 *   <li>Viewing available pets and adopted pets</li>
 * </ul>
 */
public class Customer implements Serializable {
    private String customerId, name, gender, address, email, phone, password, username;
    private int birthYear;
    private List<String> adoptedPets;
    private static final long serialVersionUID = 1L;  // Add this line

    /**
     * Constructs a new {@code Customer} object with the specified details.
     *
     * @param customerId the unique ID of the customer
     * @param name the name of the customer
     * @param gender the gender of the customer
     * @param address the address of the customer
     * @param email the email address of the customer
     * @param phone the phone number of the customer
     * @param birthYear the birth year of the customer
     * @param password the password for the customer's account
     * @param username the username for the customer's account
     */
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

    /**
     * Checks if the customer is eligible to adopt a pet.
     * A customer is eligible if they are at least 21 years old.
     *
     * @return {@code true} if the customer is eligible to adopt, {@code false} otherwise.
     */
    public boolean isEligibleToAdopt() {
        int currentYear = Year.now().getValue();
        return (currentYear - birthYear) >= 21;
    }

    /**
     * Submits an adoption request for a pet.
     *
     * @param animalID the ID of the pet to be adopted
     * @return an {@code AdoptionRequest} object if the request is valid, or {@code null} if the customer is not eligible or the animal ID is invalid
     */
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

    /**
     * Cancels an adoption request for a specific pet.
     *
     * @param requests the list of existing adoption requests
     * @param animalId the ID of the pet for which the request is to be canceled
     * @return {@code true} if the request was successfully canceled, {@code false} otherwise
     */
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

    /**
     * Displays the adoption status of the customer.
     *
     * @param requests the list of adoption requests
     */
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

    /**
     * Displays a list of available pets for adoption.
     *
     * @param animals the list of all pets
     */
    public void viewPets(List<Pet> animals) {
        StringBuilder sb = new StringBuilder("Available Pets:\n\n");
        for (Pet animal : animals) {
            sb.append(animal).append("\n");
        }
        showMessage(sb.toString(), "Available Pets");
    }

    /**
     * Displays a list of pets adopted by the customer.
     */
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

    /**
     * Adds a pet to the list of adopted pets for the customer.
     *
     * @param animalID the ID of the pet to be added
     */
    public void addAdoptedPet(String animalID) {
        adoptedPets.add(animalID);
    }

    // Getter methods

    /**
     * Gets the unique ID of the customer.
     *
     * @return the customer's ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Gets the email address of the customer.
     *
     * @return the customer's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the customer.
     *
     * @return the customer's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the name of the customer.
     *
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the username of the customer.
     *
     * @return the customer's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the gender of the customer.
     *
     * @return the customer's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the address of the customer.
     *
     * @return the customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the phone number of the customer.
     *
     * @return the customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the birth year of the customer.
     *
     * @return the customer's birth year
     */
    public int getBirthYear() {
        return birthYear;
    }
}
