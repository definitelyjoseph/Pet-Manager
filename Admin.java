import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * The {@code Admin} class is responsible for managing pets and customers in the pet adoption system.
 * It includes methods for adding, removing, and editing pets, as well as approving or denying adoption requests.
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Managing the list of pets (add, remove, edit, update details)</li>
 *   <li>Approving or denying adoption requests</li>
 *   <li>Listing available pets for adoption</li>
 * </ul>
 */
public class Admin {

    private List<Pet> pets;
    private ArrayList<Customer> customers = new ArrayList<>();
    private String username, password;

    /**
     * Constructs a new {@code Admin} object with the specified username and password.
     * Initializes the list of pets by loading them from storage.
     *
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        pets = PetStorage.loadAnimals();
    }

    /**
     * Gets the username of the admin.
     *
     * @return the username of the admin
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the admin.
     *
     * @return the password of the admin
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the list of pets managed by the admin.
     *
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Saves the updated list of pets to the storage file.
     */
    public void savePetsToFile() {
        PetStorage.saveAnimals(pets);
    }

    /**
     * Adds a new pet to the list of pets.
     *
     * @param pet the {@code Pet} object to be added
     */
    public void addPet(Pet pet) {
        this.pets.add(pet);
        savePetsToFile();
        JOptionPane.showMessageDialog(null, "Pet added successfully!");
    }

    /**
     * Removes a pet from the list of pets by its ID.
     *
     * @param petID the ID of the pet to be removed
     */
    public void removePet(String petID) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(petID)) {
                pets.remove(i);
                JOptionPane.showMessageDialog(null, "Pet removed successfully!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Pet with ID " + petID + " not found.");
    }

    /**
     * Edits the details of a pet by replacing it with an updated pet object.
     *
     * @param petID the ID of the pet to be edited
     * @param updatedPet the updated {@code Pet} object
     */
    public void editPet(String petID, Pet updatedPet) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(petID)) {
                pets.set(i, updatedPet);
                JOptionPane.showMessageDialog(null, "Pet details updated successfully!");
                savePetsToFile();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Pet with ID " + petID + " not found.");
    }

    /**
     * Updates specific details of a pet.
     *
     * @param petID the ID of the pet to be updated
     * @param name the new name of the pet
     * @param breed the new breed of the pet
     * @param age the new age of the pet
     * @param gender the new gender of the pet
     */
    public void updatePetDetails(String petID, String name, String breed, int age, String gender) {
        for (Pet pet : pets) {
            if (pet.getId().equals(petID)) {
                pet.setName(name);
                pet.setBreed(breed);
                pet.setAge(age);
                pet.setGender(gender);
                JOptionPane.showMessageDialog(null, "Pet details updated successfully!");
                savePetsToFile();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Pet with ID " + petID + " not found.");
    }

    /**
     * Lists all pets that are available for adoption.
     *
     * @return a list of available pets
     */
    public ArrayList<Pet> listAvailiblePets() {
        ArrayList<Pet> availablePets = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdoptionStat()) {
                availablePets.add(pet);
            }
        }
        return availablePets;
    }

    /**
     * Approves the adoption of a pet by a customer.
     *
     * @param customerID the ID of the customer who wants to adopt the pet
     * @param petID the ID of the pet to be adopted
     * @param requests the list of adoption requests
     * @return {@code true} if the adoption is approved, {@code false} otherwise
     */
    public boolean approveAdoption(String customerID, String petID, List<AdoptionRequest> requests) {
        Pet petToAdopt = null;

        for (Pet pet : pets) {
            if (pet.getId().equals(petID)) {
                petToAdopt = pet;
                break;
            }
        }

        if (petToAdopt == null) {
            JOptionPane.showMessageDialog(null, "Pet with ID " + petID + " not found.");
            return false;
        }

        if (!petToAdopt.isAvalibleForAdoption()) {
            JOptionPane.showMessageDialog(null, "Pet " + petID + " is not available for adoption.");
            return false;
        }

        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerID)) {
                if (customer.isEligibleToAdopt()) {
                    petToAdopt.setAdoptionStat(true);

                    for (AdoptionRequest req : requests) {
                        if (req.getCustomerId().equals(customerID) && req.getAnimalID().equals(petID)) {
                            req.setStatus("Approved");
                            break;
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Pet " + petID + " has been adopted by customer " + customerID);
                    PetStorage.saveAnimals(pets);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Customer " + customerID + " is not eligible to adopt.");
                    return false;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Customer with ID " + customerID + " not found.");
        return false;
    }

    /**
     * Denies the adoption of a pet by a customer.
     *
     * @param customerID the ID of the customer who wants to adopt the pet
     * @param petID the ID of the pet to be adopted
     * @param requests the list of adoption requests
     */
    public void denyAdoption(String customerID, String petID, List<AdoptionRequest> requests) {
        Pet petToDeny = null;

        for (Pet pet : pets) {
            if (pet.getId().equals(petID)) {
                petToDeny = pet;
                break;
            }
        }

        if (petToDeny == null) {
            JOptionPane.showMessageDialog(null, "Pet with ID " + petID + " not found.");
            return;
        }

        for (AdoptionRequest req : requests) {
            if (req.getCustomerId().equals(customerID) && req.getAnimalID().equals(petID)) {
                req.setStatus("Denied");
                break;
            }
        }

        petToDeny.setAdoptionStat(false);
        JOptionPane.showMessageDialog(null, "Adoption for Pet " + petID + " has been denied.");
       
        savePetsToFile();
    }
}














