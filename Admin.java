import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Admin {
    private List<Pet> pets;
    private ArrayList<Customer> customers = new ArrayList<>();
    private String username, password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        pets = PetStorage.loadAnimals();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void savePetsToFile() {
        PetStorage.saveAnimals(pets);  // Save updated pets list to file
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
        savePetsToFile();  // Save updated pets list to file
        JOptionPane.showMessageDialog(null, "Pet added successfully!");
    }

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

    public ArrayList<Pet> listAvailiblePets() {
        ArrayList<Pet> availablePets = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdoptionStat())
                availablePets.add(pet);
        }
        return availablePets;
    }

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
                
        
        
 










