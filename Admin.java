import java.util.ArrayList;
import java.util.Date;
import java.time.Year;
import java.util.Scanner;

public class Admin  extends Date
{
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

    // Add a pet to the list
    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    // Remove a pet by its ID
    public void removePet(String petID) {
        for (int i = 0; i < pets.size(); i++) {
        
          
                if (pets.get(i).getId() == petID) {
                    pets.remove(i);
                    System.out.println("Pet removed successfully");
                    return;
                }
        
            }
            System.out.println("Pet with ID " + petID + " not found.");
        }
    

    // Edit a pet's details by its ID
    public void editPet(String petID, Pet updatedPet) {
        for (int i = 0; i < pets.size(); i++) {
         if( pets.get(i).getId() == petID) {
                    pets.set(i, updatedPet); // Replace the pet with the updated one
                    System.out.println("Pet updated successfully");
                    return;
                }
            }
        
        System.out.println("Pet with ID " + petID + " not found.");
        }

    // Update specific details of a pet
    public void updatePetDetails(String petID, String name, String breed, int age) {
        for (Pet pet : pets) {
           
                if (pet.getId() == petID) {
                    pet.setName(name);
                    pet.setBreed(breed);
                    pet.setAge(age);
                    pet.setId(petID); // Ensure the ID is set correctly
                    System.out.println("Pet details updated successfully");
                        return;
                    }
                }
            System.out.println("Pet with ID " + petID + " not found.");
        }

    public ArrayList<Pet> listAvailiblePets() {

        ArrayList<Pet> availablePets = new ArrayList<>();
        for(Pet pet : pets){
            if (!pet.getAdoptionStat() )
            availablePets.add(pet);
        }

        return availablePets;
    }

    public boolean aprovedAdoption(String CustomerID, String petID) throws NullPointerException {
        
        for (Pet pet : pets) {
            if (!pet.isAvalibleForAdoption()){
                System.out.println("Pet is not available for adoption.");
                return false;
            }
        
        for (int i=0; i<customers.size(); i++){
            if (customers.get(i).getCustomerId().equals(CustomerID) ){
                if (customers.get(i).isEligibleToAdopt()){
                    pet.setAdoptionStat(true); // Set the pet as adopted
                    System.out.println("Pet " + petID + " has been adopted by customer " + CustomerID);
                    return true;
            } else {
                System.out.println("Customer " + CustomerID + "is not eligible to adopt");
                return false;
            }
        }
    }}
    return false;
}}
   
                

                
        
        
 









