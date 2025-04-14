import java.io.Serializable;

/**
 * The {@code Pet} class represents a pet in the pet adoption system.
 * It stores details about the pet, such as its ID, name, breed, age, gender, and adoption status.
 * This class also provides methods to retrieve and update the pet's information.
 * 
 * <p>Implements {@code Serializable} to allow instances of this class to be serialized for storage or transmission.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Retrieving pet details such as name, breed, age, gender, and adoption status</li>
 *   <li>Updating pet details</li>
 *   <li>Determining the species of the pet based on its ID</li>
 *   <li>Checking if the pet is available for adoption</li>
 * </ul>
 */
public class Pet implements Serializable {

    private String aniD;
    private String name;
    private String breed;
    private int age;
    private String gender;
    private boolean adopted;
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code Pet} object with the specified details.
     *
     * @param aniD the unique ID of the pet
     * @param name the name of the pet
     * @param breed the breed of the pet
     * @param age the age of the pet
     * @param gender the gender of the pet
     */
    public Pet(String aniD, String name, String breed, int age, String gender) {
        this.aniD = aniD;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.adopted = false; // Default value for adopted
    }

    /**
     * Gets the name of the pet.
     *
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the breed of the pet.
     *
     * @return the breed of the pet
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Determines the species of the pet based on the first letter of its ID.
     * 
     * <p>For example:</p>
     * <ul>
     *   <li>"D_12345" returns "Dog"</li>
     *   <li>"C_12345" returns "Cat"</li>
     *   <li>"B_12345" returns "Bird"</li>
     * </ul>
     *
     * @return the species of the pet, or "Unknown" if the ID format is not recognized
     */
    public String getSpecies() {
        switch (aniD.split("_")[0]) {
            case "D":
                return "Dog";
            case "C":
                return "Cat";
            case "B":
                return "Bird";
            case "R":
                return "Reptile";
            case "F":
                return "Fish";
            default:
                return "Unknown";
        }
    }

    /**
     * Gets the unique ID of the pet.
     *
     * @return the ID of the pet
     */
    public String getId() {
        return aniD;
    }

    /**
     * Gets the age of the pet.
     *
     * @return the age of the pet
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the gender of the pet.
     *
     * @return the gender of the pet
     */
    public String getGender() {
        return gender;
    }

    /**
     * Checks if the pet has been adopted.
     *
     * @return {@code true} if the pet has been adopted, {@code false} otherwise
     */
    public boolean getAdoptionStat() {
        return adopted;
    }

    /**
     * Sets the adoption status of the pet.
     *
     * @param adopted {@code true} if the pet is adopted, {@code false} otherwise
     */
    public void setAdoptionStat(boolean adopted) {
        this.adopted = adopted;
    }

    /**
     * Sets the name of the pet.
     *
     * @param name the new name of the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the age of the pet.
     *
     * @param age the new age of the pet
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets the breed of the pet.
     *
     * @param breed the new breed of the pet
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * Sets the gender of the pet.
     *
     * @param gender the new gender of the pet
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the unique ID of the pet.
     *
     * @param aniD the new ID of the pet
     */
    public void setId(String aniD) {
        this.aniD = aniD;
    }

    /**
     * Returns a string representation of the pet, including its ID, name, breed, age, and adoption status.
     *
     * @return a string representation of the pet
     */
    @Override
    public String toString() {
        return "Pet ID: " + aniD + ", Name: " + name + ", Breed: " + breed + ", Age: " + age + ", Adopted: " + adopted;
    }

    /**
     * Checks if the pet is available for adoption.
     *
     * @return {@code true} if the pet is available for adoption, {@code false} otherwise
     */
    public boolean isAvalibleForAdoption() {
        return !adopted;
    }
}