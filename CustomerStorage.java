import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code CustomerStorage} class is responsible for handling the storage and retrieval of customer data
 * in the pet adoption system. It provides methods to save, load, and manage customer records using file-based storage.
 * 
 * <p>This class uses serialization to persist customer data to a file and retrieve it when needed.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Saving customer data to a file</li>
 *   <li>Loading customer data from a file</li>
 *   <li>Adding new customers to the storage</li>
 *   <li>Retrieving a customer by their unique ID</li>
 * </ul>
 */
public class CustomerStorage {
    private static final String FILE_NAME = "customers.dat";

    /**
     * Loads the list of customers from the file.
     * If the file does not exist or cannot be read, an empty list is returned.
     *
     * @return a {@code List<Customer>} containing all customers loaded from the file
     */
    @SuppressWarnings("unchecked")
    public static List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            customers = (List<Customer>) ois.readObject(); // Explicit casting to ArrayList<Customer>
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // If file is not found or file content is incorrect, it will print error
        }
        return customers;
    }

    /**
     * Saves the list of customers to the file.
     *
     * @param customers the {@code List<Customer>} to be saved
     */
    public static void saveCustomers(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new customer to the storage.
     * The customer is added to the existing list of customers, and the updated list is saved to the file.
     *
     * @param customer the {@code Customer} object to be added
     */
    public static void addCustomer(Customer customer) {
        List<Customer> customers = loadCustomers();
        customers.add(customer);
        saveCustomers(customers);
    }
  

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param customerId the unique ID of the customer to retrieve
     * @return the {@code Customer} object with the specified ID, or {@code null} if no customer is found
     */
    public static Customer getCustomerById(String customerId) {
        List<Customer> customers = loadCustomers();
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null; // Return null if no customer found with the given ID
    }
}
