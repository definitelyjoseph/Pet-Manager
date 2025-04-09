import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer implements Serializable {
    private String customerId, name, gender, address, email, phone, password, username;
    private int birthYear;
    private List<String> adoptedPets;

    public Customer(String name, String gender, String address, String email, String phone, int birthYear, String password, String username) {
        this.customerId = generateCustomerId();
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

    private String generateCustomerId() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public boolean isEligibleToAdopt() {
        int currentYear = Year.now().getValue();
        return (currentYear - birthYear) >= 21;
    }

    public AdoptionRequest requestAdoption(String animalID) {
        if (isEligibleToAdopt()) {
            return new AdoptionRequest(email, animalID);
        } else {
            System.out.println("You must be at least 21 years old to adopt a pet.");
            return null;
        }
    }

    public void viewAdoptionStatus(List<AdoptionRequest> requests) {
        for (AdoptionRequest req : requests) {
            if (req.getCustomerEmail().equals(this.email)) {
                System.out.println(req);
            }
        }
    }

    public void viewPets(List<Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }

    public void viewAdoptedPets() {
        if (adoptedPets.isEmpty()) {
            System.out.println("No pets adopted yet.");
        } else {
            for (String petId : adoptedPets) {
                System.out.println("Adopted Pet ID: " + petId);
            }
        }
    }

    public void addAdoptedPet(String animalID) {
        adoptedPets.add(animalID);
    }

    public String getCustomerId() { return customerId; }
    public String getEmail() { return email; }
    public String getPassword() { return password;}
    public String getName() { return name; }
    public String getUsername () { return username; }
    public String getGender() { return gender; }    
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public int getBirthYear() { return birthYear; }


}