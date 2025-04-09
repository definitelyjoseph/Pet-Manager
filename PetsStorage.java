 import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalStorage {
    private static final String FILE_NAME = "Pets.dat";

    public static void saveAnimals(List<Animal> pets) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(pets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Animal> loadAnimals() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Animal>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
} 