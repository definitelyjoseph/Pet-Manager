import java.io.Serializable;

public class AdoptionRequest implements Serializable {
    private String customerId, animalID;
    private String status; // Pending, Approved, Denied

    public AdoptionRequest(String customerId, String animalID) {
        this.customerId = customerId;
        this.animalID = animalID;
        this.status = "Pending";
    }

    public String getCustomerId() { return customerId; }
    public String getAnimalID() { return animalID; }
    public String getStatus() { return status; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setAnimalID(String animalID) { this.animalID = animalID; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Customer: " + customerId + ", Animal ID: " + animalID + ", Status: " + status;
    }
}
