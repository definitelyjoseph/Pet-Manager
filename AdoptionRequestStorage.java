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

    @SuppressWarnings("unchecked")
    public static ArrayList<AdoptionRequest> loadRequests() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<AdoptionRequest>) in.readObject(); // Returns List<AdoptionRequest>
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // If loading fails, return an empty list
        }
    }

    public static void addRequest(AdoptionRequest request) {
        ArrayList<AdoptionRequest> requests = loadRequests();
        requests.add(request);
        saveRequests(requests);
    }

   
    public static List<AdoptionRequest> getRequestsByCustomerId(String customerId) {
        ArrayList<AdoptionRequest> requests = loadRequests();
        List<AdoptionRequest> customerRequests = new ArrayList<>();
        for (AdoptionRequest request : requests) {
            if (request.getCustomerId().equals(customerId)) {
                customerRequests.add(request);
            }
        }
        return customerRequests;
    }
}
 