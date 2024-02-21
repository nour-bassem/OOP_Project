import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.stage.Stage;

public class AdminGUI extends Application {
    // confirmButton confirmButtonn, String u,p in log in String uSU,pSU
    public Stage primaryStage;
    private VBox loginPanel;
    private VBox SUpanel;
    private Admin admin;
    private TextField userNameTextField;
    private PasswordField passwordField;
    private Label loginStatusLabel;
    private int loginAttempts = 0;
    private TextField signupUserNameTextField;
    private PasswordField signUpPasswordField;
    private PasswordField passwordConfirmField;
    private Label signupStatusLabel;
    private final int MAX_LOGIN_ATTEMPTS = 4;
    /* !!DYNAMIC PATH */

    String backGroundPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/background.jpg";
    String homeIconPath = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\home.png";
    String cartIconPath = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\cart.png";
    String ordersTrendIcon = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\trends.png";
    String usersGraphIcon = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\users trends.png";
    String manageUsersIcon = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\users (1).png";
    String productsIcon = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\products.png";
    String truckOnmanageSuppliers = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\truck.png";
    String iconOn_viewListOfSuppliersButton = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\truck.png";
    String iconOn_SuppliersTrendsButton = "\"E:\\MIU STUDIES\\Year 2\\S1\\OOP\\project 6\\iconss\\truck.png";

    public AdminGUI() {
        admin = new Admin();
        admin.readFromFile();
    }

    // LOG IN OR SIGN UP BUTTONS
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Registering");

        Button loginButton = createPurpleButton("Log-in");
        Button signUpButton = createPurpleButton("Sign-up");

        loginButton.setOnAction(e -> slideLogInPanel());
        signUpButton.setOnAction(e -> slideSignUpPanel());

        VBox buttonBox = new VBox(10, loginButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER); // Center the buttons in the HBox
        buttonBox.setPadding(new Insets(20, 20, 20, 20));

        loginPanel = createLoginPanel();
        loginPanel.setTranslateX(-800); // Initial translation to hide the panel

        SUpanel = createSignUpPanel();
        SUpanel.setTranslateX(800);

        setBackgroundImage(buttonBox);

        StackPane root = new StackPane();
        root.getChildren().addAll(buttonBox, loginPanel, SUpanel);

        Scene scene = new Scene(root, 800, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /* LOG IN */
    // log in panel
    private VBox createLoginPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);");

        // Create the login scene components in a method and put it in a vbox
        VBox loginFieldsScene = createLoginFieldsScene();
        // Add the vbox on the panel
        panel.getChildren().add(loginFieldsScene);
        // Set alignment for the login fields scene
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    // THE SLIDING movement
    // Update: A THREAD(HIDE AND SEEK) ONLY 1 PANEL AT A TIME

    private void slideLogInPanel() {
        if (SUpanel.getTranslateX() == 510) {
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), SUpanel);
            t1.setToX(800);
            t1.setOnFinished(e -> {
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
                boolean isVisible = loginPanel.getTranslateX() == 0;
                double targetTranslation = isVisible ? -loginPanel.getWidth() : -510;
                tt.setToX(targetTranslation);
                tt.play();
            });
            t1.play();
        } else {
            // If SignUp panel is not visible, slide in LogIn panel
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
            boolean isVisible = loginPanel.getTranslateX() == 0;
            double targetTranslation = isVisible ? -loginPanel.getWidth() : -510;
            tt.setToX(targetTranslation);
            tt.play();
        }
    }

    // Create the login components and scene
    private VBox createLoginFieldsScene() {
        VBox loginFieldsScene = new VBox(10);
        userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter your username");
        userNameTextField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");
        Button confirmButton = createPurpleButton("Login");
        confirmButton.setOnAction(e -> handleLogin());

        loginStatusLabel = new Label("");
        loginStatusLabel.setWrapText(true);
        loginFieldsScene.getChildren().addAll(userNameTextField, passwordField, confirmButton, loginStatusLabel);

        // Set margins to create space between elements
        VBox.setMargin(userNameTextField, new Insets(50, 40, 10, 540)); // top, right, bottom, left
        VBox.setMargin(passwordField, new Insets(0, 40, 10, 540));
        VBox.setMargin(confirmButton, new Insets(0, 60, 10, 570));
        VBox.setMargin(loginStatusLabel, new Insets(0, 10, 0, 550));

        return loginFieldsScene;
    }

    private void handleLogin() {
        if (loginAttempts < MAX_LOGIN_ATTEMPTS) {
            String u = userNameTextField.getText();
            String p = passwordField.getText();
            boolean loginSuccess = admin.checkUserData(u, p);

            loginAttempts++;

            if (loginSuccess) {
                // REDIRECTING NOT CHANGE OF LABEL
                loginStatusLabel.setText("Login successful!");
                loginStatusLabel.setStyle(
                        "-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: purple; -fx-font-size: 16px;-fx-font-family: 'comic sans';");
                primaryStage.close();
                openAdminMainMenu();

            } else {
                loginStatusLabel.setText("Login failed. Check your credentials. Attempts left: "
                        + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                loginStatusLabel.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");

                // Clear text fields if login fails
                userNameTextField.clear();
                passwordField.clear();
            }

            if (!loginSuccess && loginAttempts == MAX_LOGIN_ATTEMPTS) {
                // Closing when exceeding trials
                primaryStage.close();
            }
        }
    }

    /* END OF LOGGGING IN */

    /**********************************************************************************
     * ********************************************************************************
     */

    /* SIGN UP */
    private VBox createSignUpPanel() {
        VBox panelS = new VBox(10);
        panelS.setStyle("-fx-background-color: rgba(100, 100, 100, 0.3);");

        // Create the login scene components in a method and put it in a vbox
        VBox signUpFieldsScene = createSignuPFieldsScene();
        // Add the vbox on the panel
        panelS.getChildren().add(signUpFieldsScene);
        // Set alignment for the login fields scene
        panelS.setAlignment(Pos.CENTER);
        return panelS;
    }

    private void slideSignUpPanel() {
        if (loginPanel.getTranslateX() == -510) {
            // If LogIn panel is out, hide it first
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
            tt.setToX(-loginPanel.getWidth());
            tt.setOnFinished(e -> {
                // After LogIn panel hides, show SignUp panel
                TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), SUpanel);
                boolean isVisible = SUpanel.getTranslateX() == 0;
                double targetTranslationn = isVisible ? SUpanel.getWidth() : 510;
                t1.setToX(targetTranslationn);
                t1.play();
            });
            tt.play();
        } else {
            // If LogIn panel is not visible, show SignUp panel normally
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), SUpanel);
            boolean isVisible = SUpanel.getTranslateX() == 0;
            double targetTranslationn = isVisible ? SUpanel.getWidth() : 510;
            t1.setToX(targetTranslationn);
            t1.play();
        }
    }

    // Create the login components and scene
    private VBox createSignuPFieldsScene() {
        VBox signUpFieldsScene = new VBox(10);

        signupUserNameTextField = new TextField();
        signupUserNameTextField.setPromptText("Enter your username");
        signupUserNameTextField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        signUpPasswordField = new PasswordField();
        signUpPasswordField.setPromptText("Enter your password");
        signUpPasswordField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("confirm your password");
        passwordConfirmField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        Button confirmButtonn = createPurpleButton("Sign-Up");
        confirmButtonn.setOnAction(e -> handleSignup());

        signupStatusLabel = new Label("");
        signupStatusLabel.setWrapText(true);

        signUpFieldsScene.getChildren().addAll(signupUserNameTextField, signUpPasswordField, passwordConfirmField,
                confirmButtonn, signupStatusLabel);

        // Set margins to create space between elements
        VBox.setMargin(signupUserNameTextField, new Insets(20, 540, 10, 40)); // top, right, bottom, left
        VBox.setMargin(signUpPasswordField, new Insets(0, 540, 10, 40));
        VBox.setMargin(passwordConfirmField, new Insets(0, 540, 10, 40));
        VBox.setMargin(confirmButtonn, new Insets(0, 60, 0, 50));
        VBox.setMargin(signupStatusLabel, new Insets(0, 540, 0, 70));

        return signUpFieldsScene;
    }

    private void handleSignup() {
        String uSU = signupUserNameTextField.getText();
        String pSU = signUpPasswordField.getText();
        String cpSU = passwordConfirmField.getText();

        boolean repeatedUserName = admin.checkUserName(uSU);
        // Password confirmed, not repeated username
        if (pSU.equals(cpSU) && !repeatedUserName) {
            admin.signUp(uSU, pSU);
            admin.writeToFile();
            signupStatusLabel.setText("Signed-up successfully.Redirecting...");

            // Delay for second before redirecting
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.schedule(() -> {
                Platform.runLater(() -> openAdminMainMenu());
                executorService.shutdown();
            }, 1, TimeUnit.SECONDS);

        } // repeated username
        else if (repeatedUserName) {
            signupStatusLabel.setText("User Name: (" + uSU + ") already taken. Try another user name.");
            userNameTextField.clear();
        } // passwords dosnt match
        else if (!pSU.equals(cpSU)) {
            signupStatusLabel.setText("Passwords don't match, try again.");
            signUpPasswordField.clear();
            passwordConfirmField.clear();
        }
    }

    /* END OF SIGN UP */

    /**********************************************************************************
     * ********************************************************************************
     * *******************************************************************************
     * *******************************************************************************
     */

    /* OPENNING ADMIN MAIN MENU */
    // ::MISSING: logo
    // ::MIssing: Text fit to button
    // ::Missing: suppliers trends button image
    private void openAdminMainMenu() {
        primaryStage.setTitle("Admin Main Menu");

        /* manage users button */
        Button manageUsers = createMainMenuButton("Manage Users", manageUsersIcon);
        manageUsers.setOnAction(e -> showManageUsersScene());

        /* Manage Suppliers button */
        Button manageSuppliers = createMainMenuButton("Manage Suppliers", truckOnmanageSuppliers);
        manageSuppliers.setOnAction(e -> showManageSuppliersScene());

        /* manage products button */
        Button manageProducts = createMainMenuButton("Manage Products", productsIcon);
        manageProducts.setOnAction(e -> showManageProductsScene());

        /* users trends button */
        Button userTrends = createMainMenuButton("Users Trends", usersGraphIcon);
        userTrends.setOnAction(e -> showUsersTrendsScene());

        /* Orders trends button */
        Button ordersTrends = createMainMenuButton("Orders Trends", ordersTrendIcon);
        ordersTrends.setOnAction(e -> showOrdersTrendsScene());

        // The 2 flexible rows of butttons
        FlowPane firstRowLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        firstRowLayout.setAlignment(Pos.CENTER);
        FlowPane secondRowLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        secondRowLayout.setAlignment(Pos.CENTER);

        firstRowLayout.getChildren().addAll(manageUsers, manageSuppliers, manageProducts);
        secondRowLayout.getChildren().addAll(ordersTrends, userTrends);

        // /*logo */
        // FlowPane iconPane = new FlowPane();
        // ImageView iconView = new ImageView(new
        // Image("file:C:/Users/shex/Desktop/CS/OOP/project/Screenshot_2024-01-07_010104-removebg-preview.png"));
        // iconPane.getChildren().add(iconView);
        // iconPane.setAlignment(Pos.TOP_RIGHT);
        // iconView.setFitWidth(100);
        // iconView.setFitHeight(100);
        // addicon to vbox

        VBox mainMenuLayout = new VBox(10, firstRowLayout, secondRowLayout);
        mainMenuLayout.setAlignment(Pos.CENTER);
        setBackgroundImage(mainMenuLayout);

        Scene mainMenuScene = new Scene(mainMenuLayout, 800, 450);
        primaryStage.setScene(mainMenuScene);

        // Show the primaryStage
        primaryStage.show();
    }

    /*
     * MOE---EVENT HANDLING-------------------------------------------------------
     */ // switching scenes next to the previous butons when the user clicks on it
        // addUserButton->add a user scene appear
        // removeUserButton->remove user scene
        // searchUser
        // editUser

    // MANAGEE USERS SCENE
    private void showManageUsersScene() {
        BorderPane manageUsersLayout = new BorderPane();
        setBackgroundImage(manageUsersLayout);
        primaryStage.setTitle("Manage Users");

        Button h = HOME();
        h.setOnAction(e -> openAdminMainMenu());
        BorderPane.setAlignment(h, Pos.TOP_RIGHT); // Set alignment to top right
        BorderPane.setMargin(h, new Insets(10, 10, 0, 0)); // Optional: Set margin for spacing

        Button addUserButton = createPurpleButton("Add User");
        // ::Event Handle
        Button removeUserButton = createPurpleButton("Remove User");
        // ::Event Handle
        Button searchUser = createPurpleButton("Search for User");
        // ::Event Handle
        Button editUser = createPurpleButton("editUser");
        // ::Event Handle

        VBox buttonsVBox = new VBox(10, addUserButton, editUser, searchUser, removeUserButton);
        buttonsVBox.setPadding(new Insets(0, 20, 0, 20));
        buttonsVBox.setAlignment(Pos.CENTER_LEFT);

        manageUsersLayout.setTop(h);
        manageUsersLayout.setLeft(buttonsVBox);

        Scene manageUsersScene = new Scene(manageUsersLayout, 800, 450);
        primaryStage.setScene(manageUsersScene);

        primaryStage.show();
    }

    private void showManageSuppliersScene() {

        BorderPane manageSuppliersLayout = new BorderPane();

        setBackgroundImage(manageSuppliersLayout);
        primaryStage.setTitle("Manage Suppliers");

        // right top corner home button
        Button h1 = HOME();
        h1.setOnAction(e -> openAdminMainMenu());
        BorderPane.setAlignment(h1, javafx.geometry.Pos.TOP_RIGHT);
        manageSuppliersLayout.setTop(h1);
        manageSuppliersLayout.setPadding(new Insets(10, 10, 0, 0));

        Button viewListOfSuppliersButton = createMainMenuButton("Suppliers List", iconOn_viewListOfSuppliersButton);
        // ::Event Handle
        Button SuppliersTrendsButton = createMainMenuButton("SuppliersTrend", iconOn_SuppliersTrendsButton);
        // ::Event Handle

        HBox twoButtons = new HBox(10);
        twoButtons.setAlignment(Pos.CENTER);
        twoButtons.getChildren().addAll(viewListOfSuppliersButton, SuppliersTrendsButton);

        manageSuppliersLayout.setCenter(twoButtons);

        // manageSuppliersLayout.getChildren().addAll();
        Scene manageUsersScene = new Scene(manageSuppliersLayout, 800, 450);
        primaryStage.setScene(manageUsersScene);

        primaryStage.show();

    }

    // ::IDK aout the elements of the window so add it and event handle it
    private void showManageProductsScene() {

        FlowPane manageProductssElements = new FlowPane(Orientation.HORIZONTAL, 10, 10);

        setBackgroundImage(manageProductssElements);
        primaryStage.setTitle("Mange Products");

        // right top corner home button
        Button h2 = HOME();
        h2.setOnAction(e -> openAdminMainMenu());
        FlowPane.setMargin(h2, new Insets(10, 0, 0, 710));

        manageProductssElements.getChildren().addAll(h2);
        Scene manageProductsScene = new Scene(manageProductssElements, 800, 450);
        primaryStage.setScene(manageProductsScene);
        manageProductssElements.getChildren().addAll(h2);

        primaryStage.show();

    }

    // ::switching scene to Users Trends Scene
    private void showUsersTrendsScene() {
        FlowPane usersTrendsLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        // usersTrendsLayout.setAlignment(Pos.CENTER);
        setBackgroundImage(usersTrendsLayout);
        primaryStage.setTitle("Users Trends");

        // right top corner home button
        Button h2 = HOME();
        h2.setOnAction(e -> openAdminMainMenu());
        FlowPane.setMargin(h2, new Insets(10, 0, 0, 710));

        usersTrendsLayout.getChildren().addAll(h2);

        Scene manageUsersScene = new Scene(usersTrendsLayout, 800, 450);
        primaryStage.setScene(manageUsersScene);

        primaryStage.show();

    }

    // :: Switching scene to showOrdersTrends
    private void showOrdersTrendsScene() {

        FlowPane OrdersTrendsLayout = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        // OrdersTrendsLayout.setAlignment(Pos.CENTER);

        setBackgroundImage(OrdersTrendsLayout);
        primaryStage.setTitle("Orders Trends");

        // right top corner home button

        Button h3 = HOME();
        h3.setOnAction(e -> openAdminMainMenu());
        FlowPane.setMargin(h3, new Insets(10, 0, 0, 710));

        OrdersTrendsLayout.getChildren().addAll(h3);

        Scene manageUsersScene = new Scene(OrdersTrendsLayout, 800, 450);
        primaryStage.setScene(manageUsersScene);

        primaryStage.show();

    }

    /****************************************
     * *************************************************************************
     * **********************************************************************
     *********************************************************************************
     * ********************************************************************************
     */

    /* Design */
    private void setBackgroundImage(Region region) {
        Screen screen = Screen.getPrimary();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(backGroundPath),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight(), false,
                        false, false, true));
        Background background = new Background(backgroundImage);
        region.setBackground(background);
    }

    // PURPLE BUTTONS
    private Button createPurpleButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setStyle(
                "-fx-background-color: #615BA2; -fx-text-fill: white; -fx-font-size: 20; -fx-pref-width: 200; -fx-pref-height: 50; -fx-background-radius: 25;");
        return button;
    }

    // Transparent buttons with icons
    private Button createMainMenuButton(String buttonTitle, String iconPNGPath) {
        // Size variables
        double buttonWidth = 170;
        double buttonHeight = 170;
        double imageWidth = 100;
        double imageHeight = 150;

        Button tansButton = new Button(buttonTitle);
        tansButton.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);" + "-fx-text-fill: black;"
                + "-fx-font-size: 18px;" + "-fx-font-family:  Open Sans;");
        tansButton.setMinSize(buttonWidth, buttonHeight);
        tansButton.setMaxSize(buttonWidth, buttonHeight);
        ImageView iconOnButton = new ImageView(new Image(iconPNGPath));
        iconOnButton.setFitWidth(imageWidth);
        iconOnButton.setFitHeight(imageHeight);
        iconOnButton.setPreserveRatio(true);
        tansButton.setGraphic(iconOnButton);
        tansButton.setContentDisplay(ContentDisplay.TOP);

        return tansButton;
    }

    // Home button
    private Button HOME() {
        Button homeButton = new Button("");

        ImageView iconOnButton = new ImageView(new Image(homeIconPath));
        iconOnButton.setFitWidth(60);
        iconOnButton.setFitHeight(60);
        iconOnButton.setPreserveRatio(true);
        homeButton.setGraphic(iconOnButton);
        homeButton.setContentDisplay(ContentDisplay.CENTER);
        // css circle button
        homeButton.setStyle(
                "-fx-shape: \"M 15 15 A 15 15 0 1 0 15.001 15 Z\"; -fx-alignment: center;-fx-background-color: rgba(100, 100, 100, 0.2);");

        return homeButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
