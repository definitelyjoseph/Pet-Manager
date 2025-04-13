import java.io.Serializable;
/**here we define the AdoptionRequest class which is responsible for managing  Adoption request*/
public class AdoptionRequest implements Serializable {
    private String customerId, animalID;
    private String status; // Pending, Approved, Denied
/**here we define the constructor for Adoption request which intializes customerId, animalID and status of the request
 * @param customerId the ID of the customer making the request
 * @param animalID the ID of the animal being requested for adoption
 * @param status the status of the request (Pending, Approved, Denied)
*/
    public AdoptionRequest(String customerId, String animalID) {
        this.customerId = customerId;
        this.animalID = animalID;
        this.status = "Pending";
    }
    /**here we define the method getCustomerId which returns the customerId of the request
     * @return the customerId of the request
    */
 
    public String getCustomerId() { return customerId; } 
    
    
      /**here we define the method getAnimalID which returns the animalID of the request
     * @return the animalID of the request*/
    
    public String getAnimalID() { return animalID; }
    /**here we define the method getStatus which returns the status of the request
     * @return the status of the request
    */
    public String getStatus() { return status; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setAnimalID(String animalID) { this.animalID = animalID; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Customer: " + customerId + ", Animal ID: " + animalID + ", Status: " + status;
    }
}
