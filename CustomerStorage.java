import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerStorage {
    private static final String FILE_NAME = "customers.dat";

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

    public static void saveCustomers(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addCustomer(Customer customer) {
        List<Customer> customers = loadCustomers();
        customers.add(customer);
        saveCustomers(customers);
    }
  

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
