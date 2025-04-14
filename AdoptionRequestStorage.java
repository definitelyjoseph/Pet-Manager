import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AdoptionRequestStorage} class is responsible for handling the storage and retrieval of adoption requests
 * in the pet adoption system. It provides methods to save, load, add, and retrieve adoption requests using file-based storage.
 * 
 * <p>This class uses serialization to persist adoption request data to a file and retrieve it when needed.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Saving adoption requests to a file</li>
 *   <li>Loading adoption requests from a file</li>
 *   <li>Adding a new adoption request to the storage</li>
 *   <li>Retrieving adoption requests by customer ID</li>
 * </ul>
 */
public class AdoptionRequestStorage {

    private static final String FILE_NAME = "adoption_requests.dat";

    /**
     * Saves the list of adoption requests to the storage file.
     *
     * @param requests the {@code List<AdoptionRequest>} to be saved
     */
    public static void saveRequests(List<AdoptionRequest> requests) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(requests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the list of adoption requests from the storage file.
     * If the file does not exist or cannot be read, an empty list is returned.
     *
     * @return an {@code ArrayList<AdoptionRequest>} containing all adoption requests loaded from the file
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<AdoptionRequest> loadRequests() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<AdoptionRequest>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Adds a new adoption request to the storage.
     * The request is added to the existing list of adoption requests, and the updated list is saved to the file.
     *
     * @param request the {@code AdoptionRequest} object to be added
     */
    public static void addRequest(AdoptionRequest request) {
        ArrayList<AdoptionRequest> requests = loadRequests();
        requests.add(request);
        saveRequests(requests);
    }

    /**
     * Retrieves all adoption requests made by a specific customer.
     *
     * @param customerId the ID of the customer whose adoption requests are to be retrieved
     * @return a {@code List<AdoptionRequest>} containing all adoption requests made by the specified customer
     */
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
