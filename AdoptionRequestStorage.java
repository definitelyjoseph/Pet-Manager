import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptionRequestStorage {
    private static final String FILE_NAME = "adoption_requests.dat";

    public static void saveRequests(List<AdoptionRequest> requests) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(requests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<AdoptionRequest> loadRequests() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<AdoptionRequest>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
 