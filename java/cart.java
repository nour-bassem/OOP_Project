
// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class cart implements Serializable {
    private static final long serialVersionUID = 1L;

    public String customerName;
    public String cashierName;
    public String customerID;
    public String casheirID;
    public double totalAmount;
    public double Rating;

    public ArrayList<String> _allofcartsinfile = new ArrayList<>();
    // public static ArrayList<Products> currentCart = new ArrayList<>();//Products
    // added to the cart
    public Customer customerrr1Obj = new Customer();
    public static String cartFilePath = "cartFile.ser";
    public Products pro = new Products();

    public cart() {
    }

    public void cartCreation() {
        _allofcartsinfile.add(CustomerGUI.userName);
        for (Products a : customerrr1Obj.currentCart) {
            _allofcartsinfile.add(a.productName);
            _allofcartsinfile.add(String.valueOf(a.price));
            _allofcartsinfile.add(String.valueOf(Rating));
        }
    }

    // Writee updated array list of carts in a binary file
    public void writeToFileOFCARTSHISTORY() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cartFilePath))) {
            oos.writeObject(_allofcartsinfile);
            System.out.println("ArrayList written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read ArrayList from binary file
    public void readFromFileOFCARTSHISTORY() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cartFilePath))) {
            _allofcartsinfile = (ArrayList<String>) ois.readObject();
            System.out.println("ArrayList read from file successfully.");
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}