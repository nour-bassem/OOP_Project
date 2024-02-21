
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    public String customerUserName;
    public String customerID;
    private String customerPassword;
    private String customerConfirmPass;
    public ArrayList<Customer> customerDataList = new ArrayList<>();
    public String customerFileName = "customerData.dat";
    // public cart c1;

    public static String cartFilePath = "cartFile.ser";
    public static ArrayList<Products> currentCart = new ArrayList<>();
    public static ArrayList<Products> cartItems = new ArrayList<>();
    public static ArrayList<Products> cartHistory = new ArrayList<>();

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public ArrayList<Customer> getCustomerDataList() {
        return customerDataList;
    }

    public void setCustomerDataList(ArrayList<Customer> customerDataList) {
        this.customerDataList = customerDataList;
    }

    public Customer() {
    }

    public Customer(String customerUserName, String customerPassword) {
        this.customerUserName = customerUserName;
        this.customerPassword = customerPassword;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerConfirmPass() {
        return customerConfirmPass;
    }

    public void setCustomerConfirmPass(String confirmPass) {
        this.customerConfirmPass = confirmPass;
    }

    @Override
    public String toString() {
        return "CustomerID:: " + this.customerID + " , ";
    }

    public void addProductToCart(String product) {

        for (Products p : Products.productsDataList) {
            if (p.productName.equals(product)) {
                if (!currentCart.contains(p)) {
                    currentCart.add(p);
                    // cartHistory.add(p);
                    System.out.println("Product added successfully");
                } else {
                    CustomerGUI.showAlert();
                }
                return; // stop searching
            }
        }
        System.out.println("Product not found");
    }

    public void removeProductFromCart(String pname) {
        for (Products p : currentCart) {
            if (p.productName.equals(pname)) {
                currentCart.remove(p);
                System.out.println("Product removed successfully");
                return; // stop searching
            }
        }
        System.out.println("Product not found");

    }

    public void viewCurrentCart() {
        System.out.println("products in your current cart are:  ");
        for (Products p : currentCart) {
            System.out.println("product ID: " + p.PID + "   " + "product Name: " + p.productName);
        }
    }

    // USER DATA
    @Override
    public void createFile() {
        try {
            File userDataFile = new File(customerFileName);
            if (userDataFile.createNewFile()) {
                System.out.println("File created successfully!");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("File is not created" + e.getMessage());
        }
    }

    @Override
    public void readFromFile() {
        File file = new File(customerFileName);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Creating a new one.");
            createFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object readObject = ois.readObject();

            if (readObject instanceof ArrayList<?>) {
                customerDataList = (ArrayList<Customer>) readObject;
                System.out.println("Customer data read from file into arraylist successfully.");
            } else {
                System.out.println("Invalid data format in the file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(customerFileName))) {
            oos.writeObject(customerDataList);
            System.out.println("Customer data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkUserData(String userName, String password) {
        for (Customer customer : customerDataList) {
            if (customer.customerUserName.equals(userName) && customer.getCustomerPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUserName(String userName) {
        for (Customer customer : customerDataList) {
            if (customer.customerUserName.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void signUp(String userName, String password) {
        Customer currentCustomer = new Customer(userName, password);
        customerDataList.add(currentCustomer);
    }

    public void launchCustomerGUI() {
        CustomerGUI.launch(CustomerGUI.class);
    }
}