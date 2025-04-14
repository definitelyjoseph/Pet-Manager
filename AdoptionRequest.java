import java.io.Serializable;
/**
 * The {@code AdoptionRequest} class is responsible for managing adoption requests in the pet adoption system.
 * It stores details about the request, such as the customer ID, the animal ID, and the status of the request.
 * 
 * <p>Implements {@code Serializable} to allow instances of this class to be serialized for storage or transmission.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Storing the customer ID and animal ID associated with the request</li>
 *   <li>Tracking the status of the request (Pending, Approved, or Denied)</li>
 *   <li>Providing methods to retrieve and update the request details</li>
 * </ul>
 */
public class AdoptionRequest implements Serializable {

    private String customerId, animalID;
    private String status; // Pending, Approved, Denied

    /**
     * Constructs a new {@code AdoptionRequest} object with the specified customer ID and animal ID.
     * The status of the request is initialized to "Pending".
     *
     * @param customerId the ID of the customer making the request
     * @param animalID the ID of the animal being requested for adoption
     */
    public AdoptionRequest(String customerId, String animalID) {
        this.customerId = customerId;
        this.animalID = animalID;
        this.status = "Pending";
    }

    /**
     * Gets the customer ID associated with the adoption request.
     *
     * @return the customer ID of the request
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Gets the animal ID associated with the adoption request.
     *
     * @return the animal ID of the request
     */
    public String getAnimalID() {
        return animalID;
    }

    /**
     * Gets the status of the adoption request.
     *
     * @return the status of the request (e.g., Pending, Approved, Denied)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the customer ID for the adoption request.
     *
     * @param customerId the new customer ID
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets the animal ID for the adoption request.
     *
     * @param animalID the new animal ID
     */
    public void setAnimalID(String animalID) {
        this.animalID = animalID;
    }

    /**
     * Sets the status of the adoption request.
     *
     * @param status the new status of the request (e.g., Pending, Approved, Denied)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the adoption request, including the customer ID,
     * animal ID, and status.
     *
     * @return a string representation of the adoption request
     */
    @Override
    public String toString() {
        return "Customer: " + customerId + ", Animal ID: " + animalID + ", Status: " + status;
    }
}