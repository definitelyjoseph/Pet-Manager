import java.io.Serializable;

public class AdoptionRequest implements Serializable {
    private String customerEmail, aniID;
    private String status; // Pending, Approved, Denied

    public AdoptionRequest(String customerEmail, String aniID) {
        this.customerEmail = customerEmail;
        this.aniID = aniID;
        this.status = "Pending";
    }

    public String getCustomerEmail() { return customerEmail; }
    public String getAniID() { return aniID; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Customer: " + customerEmail + ", Animal ID: " + aniID + ", Status: " + status;
    }
}