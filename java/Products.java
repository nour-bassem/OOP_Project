
import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Products implements Serializable {
    // data members of products
    static public int counter = 0;
    public String productName;
    public String PID;
    public int price;
    public String customerName;
    // product file
    public String productsFileName = "productsData.ser";
    static public ArrayList<Products> productsDataList = new ArrayList<>();

    public Products() {
    };

    public Products(String pname, int num) {
        this.productName = pname;
        this.PID = String.format("%05d", ++counter);
        this.price = num;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
    }

    public String getProductName() {
        return productName;
    }

    @Override

    public String toString() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return "Product Name: " + this.productName + "\nProduct ID: " + this.PID + "\n" + "Price: " + this.price;
    }

    static public void fillProducts(Products product) {
        productsDataList.add(product);

    }

}
