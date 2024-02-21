package pack1;

import java.io.*;
import java.util.ArrayList;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure consistent serialization

    public String adminUserName;package pack1;
import java.io.*;
import java.util.ArrayList;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure consistent serialization

    public String adminUserName;
    public String adminUserType;
    private String adminPassword;
    private String adminConfirmPass;
    public ArrayList<Admin> adminDataList = new ArrayList<>();
    public String adminFileName = "adminData.dat";
    public String productsFileName = "productsData.txt";

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminUserType() {
        return adminUserType;
    }

    public void setAdminUserType(String adminUserType) {
        this.adminUserType = adminUserType;
    }

    public ArrayList<Admin> getAdminDataList() {
        return adminDataList;
    }

    public void setAdminDataList(ArrayList<Admin> adminDataList) {
        this.adminDataList = adminDataList;
    }

    public Admin() {
    }

    public Admin(String adminUserName, String adminUserType, String adminPassword) {
        this.adminUserName = adminUserName;
        this.adminUserType = adminUserType;
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminConfirmPass() {
        return adminConfirmPass;
    }

    public void setAdminConfirmPass(String confirmPass) {
        this.adminConfirmPass = confirmPass;
    }

    public void ViewMaxCartCashier() {
        System.out.println("cashier with maximum number of carts");
        System.out.println(Cashier.maxnumofcarts());
    }

    void addUser(String Usernamein,String Usertypein,String Passwordin,String ConfirmPassword) {
           if (Usertypein.equalsIgnoreCase("cashier")) {
            Cashier a = new Cashier(Usernamein, Passwordin, 0);
            Cashier.CashiersDataList.add(a);
            System.out.println(Cashier.CashiersDataList.size());

        } else if (Usertypein.equalsIgnoreCase("customer")) {
            Customer a = new Customer(Usernamein, Usertypein, Passwordin);
            Customer.CustomerDataList.add(a);
            System.out.println(Customer.CustomerDataList.size());
        }
    }

     public boolean editUser(String currentusername,String newusername,String usertype,String newpassword) {
        
        if (usertype.equalsIgnoreCase("cashier")) {
            for (Cashier a : Cashier.CashiersDataList) {
                if (currentusername.equals(a.cashierUserName)) {
                    a.cashierUserName = newusername;
                    a.setCashierPassword(newpassword);
                    a.setCashierConfirmPassword(newpassword);
                    return true;
                }
            }
        } else if (usertype.equalsIgnoreCase("customer")) {
            for (Customer a : Customer.CustomerDataList) {
                if (currentusername.equals(a.customerUserName)) {
                    a.customerUserName = newusername;
                    a.setCustomerPassword(newpassword);
                    a.setCustomerConfirmPass(newpassword);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean removeUser(String removeUsername,String removeUserType) {
        
        if (removeUserType.equalsIgnoreCase("cashier")) {
            for (Cashier a : Cashier.CashiersDataList) {
                if (a.cashierUserName.equals(removeUsername)) {
                    Cashier.CashiersDataList.remove(a);
                    return true;
                }
            }
        } else if (removeUserType.equalsIgnoreCase("customer")) {
            for (Customer a : Customer.CustomerDataList) {
                if (a.customerUserName.equals(removeUsername)) {
                    Customer.CustomerDataList.remove(a);
                    return true;
                }
            }
        }

        return false; // Return false if user not found
    }

    
   public void searchUser(String searchUsername,String searchUserType) {
        
        if (searchUserType.equalsIgnoreCase("admin")) {
            for (Admin a : adminDataList) {
                if (a.adminUserName.equalsIgnoreCase(searchUsername)) {
                    System.out.println("username: " + a.adminUserName + "\n" + "user type: " + a.adminUserType);
                    return;
                }
            }
        } else if (searchUserType.equalsIgnoreCase("cashier")) {
            for (Cashier a : Cashier.CashiersDataList) {
                if (a.cashierUserName.equals(searchUsername))
                    System.out.println("username: " + a.cashierUserName + "\n" + "user type: " + a.CashierUserType);
            }
        } else if (searchUserType.equalsIgnoreCase("customer")) {
            for (Customer a : Customer.CustomerDataList) {
                if (a.customerUserName.equals(searchUsername))
                    System.out.println("username: " + a.customerUserName + "\n" + "user type: " + a.CustomerUserType);
                return;
            }
        }
        System.out.println("user not found");

    }


    
    // Checks in the array llst of admin object sfor the credientials in the
    // parameters
    @Override
    public boolean checkUserData(String userName, String password) {
        for (Admin admin : adminDataList) {
            if (admin.adminUserName.equals(userName) && admin.adminUserType.equals("Admin")
                    && admin.getAdminPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createFile() {
        try {
            File userDataFile = new File(adminFileName);
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
        File file = new File(adminFileName);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Creating a new one.");
            createFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object readObject = ois.readObject();

            if (readObject instanceof ArrayList<?>) {
                adminDataList = (ArrayList<Admin>) readObject;
                System.out.println("Admin data read from file into arraylist successfully.");
            } else {
                System.out.println("Invalid data format in the file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(adminFileName))) {
            oos.writeObject(adminDataList);
            System.out.println("Admin data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    // user name repetiotion in the same file for SIGN UP
    public boolean checkUserName(String AuserName) {
        for (Admin admin1 : adminDataList) {
            if (admin1.adminUserName.equals(AuserName))
                return true;
        }
        return false;
    }

    @Override
    public void signUp(String un, String p) {
        Admin currentAdmin = new Admin(un, "Admin", p);
        adminDataList.add(currentAdmin);
    }

    // GUI integration
    public void launchAdminGUI()
    {
        AdminGUI.launch(AdminGUI.class);
    }

}
    public String adminUserType;
    private String adminPassword;
    private String adminConfirmPass;
    public ArrayList<Admin> adminDataList = new ArrayList<>();
    public String adminFileName = "adminData.dat"; 
    public String productsFileName = "productsData.txt";

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminUserType() {
        return adminUserType;
    }

    public void setAdminUserType(String adminUserType) {
        this.adminUserType = adminUserType;
    }

    public ArrayList<Admin> getAdminDataList() {
        return adminDataList;
    }

    public void setAdminDataList(ArrayList<Admin> adminDataList) {
        this.adminDataList = adminDataList;
    }

    public Admin() {
    }

    public Admin(String adminUserName, String adminUserType, String adminPassword) {
        this.adminUserName = adminUserName;
        this.adminUserType = adminUserType;
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminConfirmPass() {
        return adminConfirmPass;
    }

    public void setAdminConfirmPass(String confirmPass) {
        this.adminConfirmPass = confirmPass;
    }
    // Checks in the array llst of admin object sfor the credientials in the
    // parameters
    @Override
    public boolean checkUserData(String userName, String password) {
        for (Admin admin : adminDataList) {
            if (admin.adminUserName.equals(userName) && admin.adminUserType.equals("Admin")
                    && admin.getAdminPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
@Override
    public void createFile() {
        try {
            File userDataFile = new File(adminFileName);
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
        File file = new File(adminFileName);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Creating a new one.");
            createFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object readObject = ois.readObject();

            if (readObject instanceof ArrayList<?>) {
                adminDataList = (ArrayList<Admin>) readObject;
                System.out.println("Admin data read from file into arraylist successfully.");
            } else {
                System.out.println("Invalid data format in the file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e);
        }
    }
@Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(adminFileName))) {
            oos.writeObject(adminDataList);
            System.out.println("Admin data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@Override
    // user name repetiotion in the same file for SIGN UP
    public boolean checkUserName(String AuserName) {
        for (Admin admin1 : adminDataList) {
            if (admin1.adminUserName.equals(AuserName))
                return true;
        }
        return false;
    }
@Override
    public void signUp(String un, String p) {
        Admin currentAdmin = new Admin(un, "Admin", p);
        adminDataList.add(currentAdmin);
    }

    // GUI integration

    

    public void launchAdminGUI() {
        AdminGUI.launch(AdminGUI.class);
    }

}
