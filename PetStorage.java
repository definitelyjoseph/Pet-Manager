import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PetStorage {
    private static final String FILE_NAME = "Pets.dat";

    public static void saveAnimals(List<Pet> animals) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(animals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

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
    
        public static List<Pet> loadAnimalsFromFile() {
        List<Pet> animals = loadAnimals();
        List<Pet> loadedAnimals = new ArrayList<>();

        for (Pet pet : animals){
            if (!pet.getAdoptionStat()) {
                loadedAnimals.add(pet);
            }
        }

        return loadedAnimals;
}

        public static void addAnimal(Pet newPet) {
            List<Pet> animals = loadAnimals();
            animals.add(newPet);
            saveAnimals(animals);
        }

        public static void removeAnimal(Pet aniID) {
            List<Pet> animals = loadAnimals();
            animals.removeIf(Pet -> Pet.getId().equals(aniID.getId()));
            saveAnimals(animals);
        }

        
}