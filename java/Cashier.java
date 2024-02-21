import java.util.ArrayList;
import java.io.*;

public class Cashier extends User implements Serializable {
    String cashierUserName;
    String cashierPassword;
    private static final long serialVersionUID = 1L; // Ensure consistent serialization
    String cashierFileName = "cashiersData.dat";

    public ArrayList<Cashier> cashierDataList = new ArrayList<>();
    String cashierConfirmPass;

    // Constructors
    public Cashier() {
    };

    public Cashier(String u, String p) {
        cashierUserName = u;
        cashierPassword = p;
    };

    // Getters and setters
    public String getCashierUserName() {
        return cashierUserName;
    }

    public String getCashierPassword() {
        return cashierPassword;
    }

    public void setCashierUserName(String u) {
        this.cashierUserName = u;
    }

    public void setCashierPassword(String p) {
        this.cashierPassword = p;
    }

    @Override
    public void createFile() {
        try {
            File userDataFile = new File(cashierFileName);
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
        File file = new File(cashierFileName);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Creating a new one.");
            createFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object readObject = ois.readObject();

            if (readObject instanceof ArrayList<?>) {
                cashierDataList = (ArrayList<Cashier>) readObject;
                System.out.println("Cashier data read from file into arraylist successfully.");
            } else {
                System.out.println("Invalid data format in the file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cashierFileName))) {
            oos.writeObject(cashierDataList);
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOGGING IN AND SIGNING UP
    @Override
    public void signUp(String un, String p) {
        Cashier currentCashier = new Cashier(un, p); // Set the userName and cashierPassword
        cashierDataList.add(currentCashier);
    }

    @Override
    public boolean checkUserData(String un, String p) {
        for (Cashier c : cashierDataList) {
            if (c.getCashierUserName().equals(un)
                    && c.getCashierPassword().equals(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUserName(String un) {
        for (Cashier c : cashierDataList) {
            if (c.getCashierUserName().equals(un))
                return true;
        }
        return false;
    }

}
