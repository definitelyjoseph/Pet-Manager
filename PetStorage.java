 import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PetStorage {
    private static final String FILE_NAME = "Pets.dat";

    public static void saveAnimals(List<Pets> animals) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(animals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Pets> loadAnimals() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Pets>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
} 