import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/** Admin Class
 * @here we define the admin class which is responsible for managing the pets and customers.
 * It includes methods for adding, removing, and editing pets, as well as approving or denying adoption requests.
 */
public class Admin {
    private List<Pet> pets;
    private ArrayList<Customer> customers = new ArrayList<>();
    private String username, password;
/** Contructor for Admin class
 * @param username the username of the admin
 * @param password the password of the admin 
 * @here we initialize the admin class with the username and password, and load the pets from the storage.
 */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        pets = PetStorage.loadAnimals();
    }
    /** Getters for the Admin class */
    /** 
     * @here we define the getUsername method which returns the username of the admin.
     */
    public String getUsername() {
        return username;
    }
    /**@here we define the getPassword method which returns the password of admin */

    public String getPassword() {
        return password;
    }

    /** @here we define the getPets method which returns the list of pets. */

    public List<Pet> getPets() {
        return pets;
    }
/** @here we define the savePetsToFile method which saves updated pets list to file. */
    public void savePetsToFile() {
        PetStorage.saveAnimals(pets);  // Save updated pets list to file
    }
/** @here we define the addPet method which adds a new pet to the list of pets. */

    public void addPet(Pet pet) {
        this.pets.add(pet);
        savePetsToFile();  // Save updated pets list to file
        JOptionPane.showMessageDialog(null, "Pet added successfully!");
    }
/** @here we define the removePet method which removes a pet from the list of pets. */

    public void removePet(String petID) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(petID)) {
                pets.remove(i);
                JOptionPane.showMessageDialog(null, "Pet removed successfully!");
                return;
            }
        }
        System.out.println("Pet with ID " + petID + " not found.");
    }
/** @here we define the editPet method which edits the details of a pet. */
    public void editPet(String petID, Pet updatedPet) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(petID)) {
                pets.set(i, updatedPet);
                JOptionPane.showMessageDialog(null, "Pet details updated successfully!");
                break;
            }
        }
        savePetsToFile();  // Save updated pets list to file
        JOptionPane.showMessageDialog(null, "Pet with ID " + petID + " not found.");
    }
/** @here we define the updatePetDetails method which updates the details of a pet. 
 * * @param petID the ID of the pet to be updated
 * @param name the new name of the pet
 * @param breed the new breed of the pet
 *@param age the new age of the pet
 *@param gender the new gender of the pet 
*/
    public void updatePetDetails(String petID, String name, String breed, int age, String gender) {
        for (Pet pet : pets) {
            if (pet.getId().equals(petID)) {
                pet.setName(name);
                pet.setBreed(breed);
                pet.setAge(age);
                pet.setGender(gender);
                JOptionPane.showMessageDialog(null, "Pet details updated successfully!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Pet with ID" +  petID + " not found.");
    }
/** @here we define the listAvailablePets method which returns a list of available pets for adoption. */
    public ArrayList<Pet> listAvailiblePets() {
        ArrayList<Pet> availablePets = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdoptionStat())
                availablePets.add(pet);
        }
        return availablePets;
    }
/** @here we define the approveAdoption method which approves the adoption of a pet by a customer.
 * @param customerID the ID of the customer who wants to adopt the pet
 * @param petID the ID of the pet to be adopted
 * @param requests the list of adoption requests
 * @return true if the adoption is approved, false otherwise*/
    public boolean approveAdoption(String customerID, String petID, List<AdoptionRequest> requests) {
        Pet petToAdopt = null;
    
        // Find the pet by petID
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
                    petToAdopt.setAdoptionStat(true);  // Mark as adopted
    
                    //  Update adoption request status
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
    /** @here we define the denyAdoption method which denies the adoption of a pet by a customer.
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
    
        //  Update adoption request status
        for (AdoptionRequest req : requests) {
            if (req.getCustomerId().equals(customerID) && req.getAnimalID().equals(petID)) {
                req.setStatus("Denied");
                break;
            }
        }
    
        petToDeny.setAdoptionStat(false);  // Still available
        JOptionPane.showMessageDialog(null, "Adoption for Pet " + petID + " has been denied.");
        PetStorage.saveAnimals(pets);
    }
}  
                
        
        
 










