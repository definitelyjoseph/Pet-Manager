import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PetStorage} class is responsible for handling the storage and retrieval of pet data
 * in the pet adoption system. It provides methods to save, load, add, and remove pets using file-based storage.
 * 
 * <p>This class uses serialization to persist pet data to a file and retrieve it when needed.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Saving the list of pets to a file</li>
 *   <li>Loading the list of pets from a file</li>
 *   <li>Adding a new pet to the storage</li>
 *   <li>Removing a pet from the storage</li>
 *   <li>Loading only pets that are available for adoption</li>
 * </ul>
 */
public class PetStorage {

    private static final String FILE_NAME = "Pets.dat";

    /**
     * Saves the list of pets to the storage file.
     *
     * @param animals the {@code List<Pet>} to be saved
     */
    public static void saveAnimals(List<Pet> animals) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(animals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the list of pets from the storage file.
     * If the file does not exist or cannot be read, an empty list is returned.
     *
     * @return a {@code List<Pet>} containing all pets loaded from the file
     */
    public static List<Pet> loadAnimals() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            @SuppressWarnings("unchecked")
            List<Pet> animals = (List<Pet>) in.readObject();
            return animals;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Loads only the pets that are available for adoption from the storage file.
     *
     * @return a {@code List<Pet>} containing pets that are not yet adopted
     */
    public static List<Pet> loadAnimalsFromFile() {
        List<Pet> animals = loadAnimals();
        List<Pet> loadedAnimals = new ArrayList<>();

        for (Pet pet : animals) {
            if (!pet.getAdoptionStat()) {
                loadedAnimals.add(pet);
            }
        }

        return loadedAnimals;
    }

    /**
     * Adds a new pet to the storage.
     * The pet is added to the existing list of pets, and the updated list is saved to the file.
     *
     * @param newPet the {@code Pet} object to be added
     */
    public static void addAnimal(Pet newPet) {
        List<Pet> animals = loadAnimals();
        animals.add(newPet);
        saveAnimals(animals);
    }

    /**
     * Removes a pet from the storage by its ID.
     * The pet is removed from the existing list of pets, and the updated list is saved to the file.
     *
     * @param aniID the {@code Pet} object to be removed
     */
    public static void removeAnimal(Pet aniID) {
        List<Pet> animals = loadAnimals();
        animals.removeIf(Pet -> Pet.getId().equals(aniID.getId()));
        saveAnimals(animals);
    }
}