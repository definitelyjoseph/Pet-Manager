import java.io.*;
import java.util.*;


public class CustomerStorage {
    private static final String FILE_NAME = "Customers.dat";

    public static List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            customers = (List<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // If file is not found, or file content is incorrect, it will print error
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
}