=import java.util.ArrayList;

public class Admin {
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private String username, password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public void removePet(String petID) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(petID)) {
                pets.remove(i);
                System.out.println("Pet removed successfully");
                return;
            }
        }
        System.out.println("Pet with ID " + petID + " not found.");
    }

    public void editPet(String petID, Pet updatedPet) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(petID)) {
                pets.set(i, updatedPet);
                System.out.println("Pet updated successfully");
                return;
            }
        }
        System.out.println("Pet with ID " + petID + " not found.");
    }

    public void updatePetDetails(String petID, String name, String breed, int age, String gender) {
        for (Pet pet : pets) {
            if (pet.getId().equals(petID)) {
                pet.setName(name);
                pet.setBreed(breed);
                pet.setAge(age);
                pet.setGender(gender);
                System.out.println("Pet details updated successfully");
                return;
            }
        }
        System.out.println("Pet with ID " + petID + " not found.");
    }

    public ArrayList<Pet> listAvailiblePets() {
        ArrayList<Pet> availablePets = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdoptionStat())
                availablePets.add(pet);
        }
        return availablePets;
    }

    public boolean aprovedAdoption(String CustomerID, String petID) {
        for (Pet pet : pets) {
            if (!pet.isAvalibleForAdoption()) {
                System.out.println("Pet is not available for adoption.");
                return false;
            }

            for (Customer customer : customers) {
                if (customer.getCustomerId().equals(CustomerID)) {
                    if (customer.isEligibleToAdopt()) {
                        pet.setAdoptionStat(true);
                        System.out.println("Pet " + petID + " has been adopted by customer " + CustomerID);
                        return true;
                    } else {
                        System.out.println("Customer " + CustomerID + " is not eligible to adopt");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}

                

                
        
        
 









