import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CustomerGUI extends Application implements Serializable {
    private Customer customer;
    private cart cArt;
    private Stage primaryStage;
    private VBox loginPanel;
    private VBox signupPanel;
    private VBox cartPanel;
    private TextField userNameTextField;
    private PasswordField passwordField;
    private Label loginStatusLabel;
    private int loginAttempts = 0;
    private TextField signupUserNameTextField;
    private PasswordField signupPasswordField;
    private PasswordField passwordConfirmField;
    private Label signupStatusLabel;
    private final int MAX_LOGIN_ATTEMPTS = 3;
    private static final DataFormat productData = new DataFormat("product");
    public static String userName;

    /* !!DYNAMIC PATH */
    String backGroundPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/background8.jpg";
    String cartIconPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/cart.png";
    String homeIconPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/home.png";
    String viewProductPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/products.png";
    String viewHistroy = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/history.png";
    String shortsPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/shorts.jpg";
    String tshirtPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/tshirt.jpg";
    String shoesPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/shoes.jpg";
    String pantsPath = "file:E:/MIU STUDIES/Year 2/S1/OOP/project 6/cloths manegment/pants.jpg";

    // Rating stars
    private static final int NUM_STARS = 5;
    private HBox ratingStars;

    private Stage stage;
    private Scene mainMenuScene;
    private Scene viewProductsScene;
    private String addedProduct;
    private String removedProduct;
    TextField txt, txt2;
    private int clickedStars = 0;
    ObservableList<Products> productsList;
    public TextField t2;
    public String enteredName;

    public CustomerGUI() {
        customer = new Customer();
        customer.readFromFile(); // USERDATA
        cArt = new cart();
        cArt.readFromFileOFCARTSHISTORY(); // CART DATA
    }

    Customer Customer = new Customer();
    Products p1 = new Products("Shorts", 100);
    Products p2 = new Products("T-Shirt", 200);
    Products p3 = new Products("Shoes", 300);
    Products p4 = new Products("Pants", 50);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Registering");

        Button loginButton = createStyledButton("Log-in");
        Button signUpButton = createStyledButton("Sign-up");

        loginButton.setOnAction(e -> slideLoginPanel());
        signUpButton.setOnAction(e -> slideSignupPanel());

        VBox buttonBox = new VBox(10, loginButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 20, 20, 20));

        loginPanel = createLoginPanel();
        loginPanel.setTranslateX(-800);

        signupPanel = createSignupPanel();
        signupPanel.setTranslateX(800);

        setBackgroundImage(buttonBox);

        StackPane root = new StackPane();
        root.getChildren().addAll(buttonBox, loginPanel, signupPanel);

        Scene scene = new Scene(root, 800, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private VBox createLoginPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);");

        VBox loginFieldsScene = createLoginFieldsScene();
        panel.getChildren().add(loginFieldsScene);
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    private void slideLoginPanel() {
        if (signupPanel.getTranslateX() == 510) {
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), signupPanel);
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
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), loginPanel);
            boolean isVisible = loginPanel.getTranslateX() == 0;
            double targetTranslation = isVisible ? -loginPanel.getWidth() : -510;
            tt.setToX(targetTranslation);
            tt.play();
        }
    }

    private VBox createLoginFieldsScene() {
        VBox loginFieldsScene = new VBox(10);
        userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter your username");
        userName = userNameTextField.getText();

        userNameTextField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        Button confirmButton = createStyledButton("Login");
        confirmButton.setOnAction(e -> handleLogin());

        loginStatusLabel = new Label("");
        loginStatusLabel.setWrapText(true);

        loginFieldsScene.getChildren().addAll(userNameTextField, passwordField, confirmButton, loginStatusLabel);

        VBox.setMargin(userNameTextField, new Insets(50, 40, 10, 540));
        VBox.setMargin(passwordField, new Insets(0, 40, 10, 540));
        VBox.setMargin(confirmButton, new Insets(0, 60, 10, 570));
        VBox.setMargin(loginStatusLabel, new Insets(0, 10, 0, 550));

        return loginFieldsScene;
    }

    private void handleLogin() {
        if (loginAttempts < MAX_LOGIN_ATTEMPTS) {
            userName = userNameTextField.getText();

            String password = passwordField.getText();

            boolean loginSuccess = customer.checkUserData(userName, password);
            loginAttempts++;

            if (loginSuccess) {
                loginStatusLabel.setText("Login successful!");
                loginStatusLabel.setStyle(
                        "-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: purple; -fx-font-size: 16px;-fx-font-family: 'comic sans';");
                primaryStage.close();
                openCustomerMainMenu();
            } else {
                loginStatusLabel.setText(
                        "Login failed. Check your credentials. Attempts left: " + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                loginStatusLabel.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");

                userNameTextField.clear();
                passwordField.clear();
            }

            if (!loginSuccess && loginAttempts == MAX_LOGIN_ATTEMPTS) {
                primaryStage.close();
            }
        }
    }

    /************************
     * End of log in ******************************
     * **********************************************Sign up***************
     */
    private VBox createSignupPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-background-color: rgba(100, 100, 100, 0.3);");

        VBox signUpFieldsScene = createSignupFieldsScene();
        panel.getChildren().add(signUpFieldsScene);
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    private void slideSignupPanel() {
        if (loginPanel.getTranslateX() == -510) {
            TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), loginPanel);
            t1.setToX(-loginPanel.getWidth());
            t1.setOnFinished(e -> {
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), signupPanel);
                boolean isVisible = signupPanel.getTranslateX() == 0;
                double targetTranslation = isVisible ? signupPanel.getWidth() : 510;
                tt.setToX(targetTranslation);
                tt.play();
            });
            t1.play();
        } else {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), signupPanel);
            boolean isVisible = signupPanel.getTranslateX() == 0;
            double targetTranslation = isVisible ? signupPanel.getWidth() : 510;
            tt.setToX(targetTranslation);
            tt.play();
        }
    }

    private VBox createSignupFieldsScene() {
        VBox signUpFieldsScene = new VBox(10);

        signupUserNameTextField = new TextField();
        signupUserNameTextField.setPromptText("Enter your username");
        signupUserNameTextField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        signupPasswordField = new PasswordField();
        signupPasswordField.setPromptText("Enter your password");
        signupPasswordField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("Confirm your password");
        passwordConfirmField.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-padding: 5px;");

        Button confirmButton = createStyledButton("Sign-up");
        confirmButton.setOnAction(e -> handleSignup());

        signupStatusLabel = new Label("");
        signupStatusLabel.setWrapText(true);

        signUpFieldsScene.getChildren().addAll(signupUserNameTextField, signupPasswordField, passwordConfirmField,
                confirmButton, signupStatusLabel);

        VBox.setMargin(signupUserNameTextField, new Insets(20, 40, 10, 40));
        VBox.setMargin(signupPasswordField, new Insets(0, 40, 10, 40));
        VBox.setMargin(passwordConfirmField, new Insets(0, 40, 10, 40));
        VBox.setMargin(confirmButton, new Insets(0, 60, 0, 50));
        VBox.setMargin(signupStatusLabel, new Insets(0, 40, 0, 70));

        return signUpFieldsScene;
    }

    private void handleSignup() {
        String username = signupUserNameTextField.getText();
        String password = signupPasswordField.getText();
        String confirmPassword = passwordConfirmField.getText();

        boolean repeatedUserName = customer.checkUserName(username);

        if (password.equals(confirmPassword) && !repeatedUserName) {
            customer.signUp(username, password);
            customer.writeToFile();
            signupStatusLabel.setText("Signed-up successfully");
        } else if (repeatedUserName) {
            signupStatusLabel.setText("Username (" + username + ") already taken. Try another username.");
            signupUserNameTextField.clear();
        } else if (!password.equals(confirmPassword)) {
            signupStatusLabel.setText("Passwords don't match. Try again.");
            signupPasswordField.clear();
            passwordConfirmField.clear();
        }
    }

    /************************************************
     * End of sign up**********************
     * ***************************************************************
     */

    // MAIN MENU OF CUSTOMER

    private void openCustomerMainMenu() {
        primaryStage.setTitle("Customer Main Menu");

        cartPanel = createCartPanel();
        cartPanel.setTranslateX(-800);

        Button viewProducts = createMainMenuButton("View Products", viewProductPath);
        viewProducts.setOnAction(e -> showViewProductsScene());

        Button viewCurrentCart = createCurrentcart();
        viewCurrentCart.setOnAction(e -> slideCartPanel());

        Button viewOrdersHistory = createMainMenuButton("View Orders History", viewHistroy);
        viewOrdersHistory.setOnAction(e -> showViewOrdersHistoryScene());

        HBox buttonsLayout = new HBox(20);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.getChildren().addAll(viewProducts, viewOrdersHistory, viewCurrentCart);

        // Set up the root layout
        StackPane root = new StackPane();
        root.getChildren().addAll(buttonsLayout);

        Scene scene = new Scene(root, 800, 450);
        primaryStage.setScene(scene);

        setBackgroundImage(root);

        primaryStage.show();
    }

    // ::NOUR EVENT HANDLING
    // ::When user clicks on a button in the main menu it changes the
    // scene to your elements in that part

    /*
     * Cart panel Items
     * I added rating stars and cofirm buttons only
     */
    private VBox createCartFieldsScene() {
        // Adjust layout and style
        VBox cartFieldsScene = new VBox(10);

        Label cartLabel = new Label("Current Cart");
        cartLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 28;");
        Label rate = new Label("Rating");
        rate.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 28;");

        // cartLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #333333;");
        Button confirmOrder = createStyledButton("Confirm cart, place order");
        confirmOrder.setWrapText(true);
        confirmOrder.setMinWidth(250);
        confirmOrder.setPrefWidth(200);
        ratingStars = createRatingStars();
        cartFieldsScene.getChildren().addAll(cartLabel, rate, ratingStars, confirmOrder);
        VBox.setMargin(cartLabel, new Insets(50, 40, 10, 400));
        VBox.setMargin(ratingStars, new Insets(50, 40, 10, 400));
        VBox.setMargin(rate, new Insets(50, 40, 10, 400));
        VBox.setMargin(confirmOrder, new Insets(50, 40, 10, 400));

        confirmOrder.setOnAction(e -> {
            try {
                handelConfirmCart();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        return cartFieldsScene;
    }

    // ::Feel free to chaange anything
    // ::just dont forget to add the home button to the top right of your layout
    // and set the background
    /* Nour */ private void showViewProductsScene() {
        primaryStage.setTitle("View products");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        Button homeButton = HOME();
        homeButton.setOnAction(e -> openCustomerMainMenu());
        gridPane.add(homeButton, 0, 0);

        FlowPane products = new FlowPane();
        Products.fillProducts(p1);
        Products.fillProducts(p2);
        Products.fillProducts(p3);
        Products.fillProducts(p4);

        // ObservableList<Products> productsList;

        if (productsList == null || productsList.isEmpty()) {
            productsList = FXCollections.observableArrayList(p1.productsDataList);
        }

        ListView<Products> listView = new ListView<>(productsList);
        listView.setOrientation(Orientation.HORIZONTAL);
        listView.setPrefSize(900, 200);

        listView.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family: Open Sans;");

        listView.setCellFactory(param -> new ListCell<Products>() {
            private final HBox graphicContainer = new HBox(10);

            @Override
            protected void updateItem(Products item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ImageView imageView = null;

                    if ("Shorts".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(shortsPath, 800, 200, true, true));
                    } else if ("T-Shirt".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(tshirtPath, 800, 200, true, true));
                    } else if ("Shoes".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(shoesPath, 800, 200, true, true));
                    } else if ("Pants".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(pantsPath, 800, 200, true, true));
                    }

                    if (imageView != null) {
                        StackPane imagePane = new StackPane(imageView);
                        graphicContainer.getChildren().setAll(imagePane);
                        setGraphic(graphicContainer);
                    }
                }
            }

        });

        products.getChildren().addAll(listView);
        gridPane.add(products, 0, 1, 8, 1);
        GridPane.setHalignment(listView, HPos.CENTER);
        GridPane.setValignment(listView, VPos.CENTER);

        listView.setOnMouseClicked(event -> {
            Products selectedItem = listView.getSelectionModel().getSelectedItem();
            System.out.println("Selected Item: " + selectedItem);
            txt.setText(selectedItem.getProductName());
        });

        Button add = createStyledButton("Add Product To Cart");
        add.setWrapText(true);
        txt = new TextField();
        txt.setAlignment(Pos.TOP_LEFT);
        txt.setMinWidth(200);
        HBox h1 = new HBox(add, txt);
        h1.setSpacing(15);
        h1.setPrefWidth(800);
        add.setMinWidth(250);
        add.setPrefWidth(200);
        gridPane.add(h1, 0, 6);

        Button remove = createStyledButton("Remove Product from Cart");
        remove.setWrapText(true);
        txt2 = new TextField();
        // removedProduct = txt2.getText();
        txt2.setAlignment(Pos.TOP_LEFT);
        txt2.setMinWidth(200);
        HBox h2 = new HBox(remove, txt2);
        h2.setSpacing(15);
        h2.setPrefWidth(800);
        remove.setMinWidth(300);
        remove.setPrefWidth(200);
        gridPane.add(h2, 1, 6);

        Button view = createStyledButton("View Products in Current Cart");
        view.setWrapText(true);
        HBox h3 = new HBox(view);
        h3.setSpacing(15);
        h3.setPrefWidth(800);
        view.setMinWidth(300);
        view.setPrefWidth(200);
        gridPane.add(h3, 0, 7, 2, 1);

        add.setOnAction(e -> handleAdd());
        remove.setOnAction(e -> handleRemove());
        view.setOnAction(e -> handleView());

        setBackgroundImage(gridPane);

        Scene scene = new Scene(gridPane, 1000, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void handleAdd() {
        String addedProduct = txt.getText();
        Customer.addProductToCart(addedProduct);
        txt.clear();
    }

    public static void showAlert() {
        Alert a = new Alert(AlertType.WARNING);
        a.setHeaderText(null);
        a.setTitle("Product in Current Cart");
        a.setContentText("Product already added");
        a.getButtonTypes().setAll(ButtonType.OK);
        a.show();
    }

    public void handleRemove() {
        removedProduct = txt2.getText();
        Customer.removeProductFromCart(removedProduct);
        txt2.clear();
    }

    public void handleView() {
        Button homeButton = HOME();
        homeButton.setOnAction(e -> openCustomerMainMenu());
        Customer.viewCurrentCart();
        ObservableList<Products> cartList = FXCollections.observableArrayList(Customer.currentCart);
        ListView<Products> listView = new ListView<>(cartList);
        listView.setOrientation(Orientation.VERTICAL);
        listView.setPrefSize(300, 500);
        listView.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family: Open Sans;");

        listView.setCellFactory(param -> new ListCell<Products>() {
            private final HBox graphicContainer = new HBox(10);

            @Override
            protected void updateItem(Products item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ImageView imageView = null;

                    if ("Shorts".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(shortsPath, 300, 500, true, true));
                    } else if ("T-Shirt".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(tshirtPath, 300, 500, true, true));
                    } else if ("Shoes".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(shoesPath, 300, 500, true, true));
                    } else if ("Pants".equals(item.getProductName())) {
                        imageView = new ImageView(new Image(pantsPath, 300, 500, true, true));
                    }

                    if (imageView != null) {
                        StackPane imagePane = new StackPane(imageView);
                        graphicContainer.getChildren().setAll(imagePane);

                        setOnDragDetected(event -> {
                            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent content = new ClipboardContent();
                            content.put(productData, item);
                            dragboard.setContent(content);
                            event.consume();
                        });

                        setGraphic(graphicContainer);
                    }
                }
            }
        });

        Button removeButton = new Button("Remove");
        removeButton.setPadding(new Insets(10));
        removeButton.setOnAction(event -> {
            Products selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                listView.getItems().remove(selectedItem);
            }
        });

        removeButton.setOnDragEntered(event -> {
            if (event.getGestureSource() != removeButton && event.getDragboard().hasContent(productData)) {
                removeButton.setStyle("-fx-background-color: red;");
            }
            event.consume();
        });

        removeButton.setOnDragExited(event -> {
            removeButton.setStyle("");
            event.consume();
        });

        removeButton.setOnDragOver(event -> {
            if (event.getGestureSource() != removeButton && event.getDragboard().hasContent(productData)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        removeButton.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasContent(productData)) {
                Products draggedProduct = (Products) dragboard.getContent(productData);
                listView.getItems().remove(draggedProduct);
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        FlowPane removeBtn = new FlowPane();
        removeBtn.getChildren().addAll(homeButton, listView, removeButton);

        FlowPane cartproducts = new FlowPane();
        cartproducts.getChildren().addAll(listView);
        VBox v1 = new VBox(createCartFieldsScene());

        HBox h1 = new HBox(cartproducts, removeBtn, v1);
        h1.setSpacing(10);
        h1.setPrefWidth(600);

        setBackgroundImage(h1);

        Scene viewScene = new Scene(h1);
        primaryStage.setScene(viewScene);
        primaryStage.show();
    }

    public void handelConfirmCart() throws IOException {

        cArt.cartCreation();
        Alert a = new Alert(AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setTitle("Thanks for ordering !");
        a.setContentText("Proceed to check out");
        a.getButtonTypes().setAll(ButtonType.NEXT, ButtonType.CANCEL);
        Optional<ButtonType> r = a.showAndWait();

        if (r.isPresent() && r.get() == ButtonType.NEXT) {
            Alert a2 = new Alert(AlertType.CONFIRMATION);
            a2.setHeaderText(null);
            a2.setTitle("Check out");
            a2.setContentText("Your Total payment is: " + totalPrice());
            a2.getButtonTypes().setAll(ButtonType.FINISH, ButtonType.CANCEL);
            Optional<ButtonType> r2 = a2.showAndWait();

            if (r2.isPresent() && r2.get() == ButtonType.CANCEL) {
                Alert a3 = new Alert(AlertType.WARNING);
                a3.setHeaderText(null);
                a3.setTitle("Cancel order");
                a3.setContentText("Are you sure want to cancel order ");
                a3.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                a3.showAndWait();
            }
        } else if (r.isPresent() && r.get() == ButtonType.CANCEL) {
            Alert a4 = new Alert(AlertType.WARNING);
            a4.setHeaderText(null);
            a4.setTitle("Cancel order");
            a4.setContentText("Are you sure want to cancel order ");
            a4.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            a4.showAndWait();
        }
        cArt.writeToFileOFCARTSHISTORY();
    }

    public double totalPrice() {
        double totalPayment = 0.0;
        for (Products product : Products.productsDataList) {
            for (Products cartProduct : Customer.currentCart) {
                if (product.productName.equals(cartProduct.productName)) {
                    totalPayment += product.price;
                    break;
                }
            }
        }
        return totalPayment;
    }

    /* Nour */ private void showViewOrdersHistoryScene() {
        primaryStage.setTitle("Orders History");

        VBox ordersHistoryElements = new VBox();

        Button homeButton = HOME();
        homeButton.setOnAction(e -> openCustomerMainMenu());

        ordersHistoryElements.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(homeButton, new Insets(10, 10, 0, 0));
        ordersHistoryElements.getChildren().addAll(homeButton);

        Text t1 = new Text("Enter customer name to view Order's History: ");
        t1.setFill(Color.WHITE);
        t1.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        t2 = new TextField();

        Button enter = createStyledButton("Enter");
        enter.setAlignment(Pos.CENTER);
        HBox b = new HBox(t1, t2);
        b.setSpacing(15);
        VBox v1 = new VBox(ordersHistoryElements, b, enter);
        v1.setAlignment(Pos.TOP_CENTER);

        setBackgroundImage(v1);
        enter.setOnAction(e -> {
            try {
                handleEnter();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(v1, 800, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void handleEnter() throws Exception {

        enteredName = t2.getText();
        Label welcomeLabel = new Label("Carts History For " + enteredName);
        welcomeLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 28;");
        VBox root = (VBox) primaryStage.getScene().getRoot();
        root.setAlignment(Pos.CENTER);

        ObservableList<String> historycartList = FXCollections.observableArrayList();

        boolean cartsFound = false;
        for (String s : cArt._allofcartsinfile) {
            if (enteredName.equals(s)) {
                cartsFound = true;
            }
        }

        if (cartsFound) {
            historycartList.add("Cart Details: " + cArt._allofcartsinfile);
            ListView<String> listView = new ListView<>(historycartList);
            listView.setOrientation(Orientation.VERTICAL);
            listView.setPrefSize(300, 500);
            listView.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);" +
                    "-fx-text-fill: black;" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-family: Open Sans;");
            root.getChildren().addAll(welcomeLabel, listView);
        } else {
            Label noCartsLabel = new Label("No carts in history for " + enteredName);
            noCartsLabel.setStyle("-fx-text-fill: white;" + "-fx-font-weight: bold;" +
                    "-fx-font-size: 20;");
            root.getChildren().addAll(welcomeLabel, noCartsLabel);
        }
    }

    // enteredName = t2.getText();
    // Label welcomeLabel = new Label("Carts History For " + userName);
    // welcomeLabel.setStyle(
    // "-fx-text-fill: white;" +
    // "-fx-font-weight: bold;" +
    // "-fx-font-size: 28;");
    // VBox root = (VBox) primaryStage.getScene().getRoot();
    // root.setAlignment(Pos.CENTER);

    // for (String s : cArt._allofcartsinfile) {
    // if (enteredName.equals(s)) {
    // ObservableList<String> historycartList = FXCollections.observableArrayList();
    // ListView<String> listView = new ListView<>(historycartList);
    // listView.setOrientation(Orientation.VERTICAL);
    // listView.setPrefSize(300, 500);
    // listView.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);" +
    // "-fx-text-fill: black;" +
    // "-fx-font-size: 18px;" +
    // "-fx-font-family: Open Sans;");
    // root.getChildren().addAll(welcomeLabel, listView);
    // } else {
    // Label noCartsLabel = new Label("No carts in history for " + userName);
    // noCartsLabel.setStyle("-fx-text-fill: white;" + "-fx-font-weight: bold;" +
    // "-fx-font-size: 20;");
    // root.getChildren().addAll(welcomeLabel, noCartsLabel);
    // }
    // }

    // Label welcomeLabel = new Label("Carts History For " + userName);
    // welcomeLabel.setStyle(
    // "-fx-text-fill: white;" +
    // "-fx-font-weight: bold;" +
    // "-fx-font-size: 28;");
    // VBox root = (VBox) primaryStage.getScene().getRoot();
    // root.setAlignment(Pos.CENTER);

    // ObservableList<String> historycartList =
    // FXCollections.observableArrayList(cArt._allofcartsinfile);
    // ListView<String> listView = new ListView<>(historycartList);
    // listView.setOrientation(Orientation.VERTICAL);
    // listView.setPrefSize(300, 500);
    // listView.setStyle("-fx-background-color: rgba(100, 100, 100, 0.2);" +
    // "-fx-text-fill: black;" +
    // "-fx-font-size: 18px;" +
    // "-fx-font-family: Open Sans;");

    // if (historycartList.isEmpty()) {
    // Label noCartsLabel = new Label("No carts in history for " + userName);
    // noCartsLabel.setStyle("-fx-text-fill: white;" + "-fx-font-weight: bold;" +
    // "-fx-font-size: 20;");
    // root.getChildren().addAll(welcomeLabel, noCartsLabel);
    // } else {
    // root.getChildren().addAll(welcomeLabel, listView);
    // }

    // // Customer.readCartsHistoryFromFile();

    // ObservableList<Products> allCarts =
    // FXCollections.observableArrayList(Customer.cartHistory);
    // // FilteredList<Products> filteredCarts = new FilteredList<>(allCarts,
    // // cartItem -> Customer.getCartItems().customerName.equals(enteredName));
    // TableView<Products> tableView = new TableView<>(allCarts);

    // TableColumn<Products, String> nameColumn = new TableColumn<>("Product Name");
    // nameColumn.setCellValueFactory(data -> new
    // SimpleStringProperty(data.getValue().getProductName()));

    // TableColumn<Products, String> priceColumn = new TableColumn<>("Price");
    // priceColumn.setCellValueFactory(data -> new
    // SimpleStringProperty(Double.toString(data.getValue().price)));

    // tableView.getColumns().addAll(nameColumn, priceColumn);
    // // VBox v1 = new VBox(welcomeLabel, tableView);
    // // Scene scene = new Scene(v1, 600, 400);
    // if (allCarts.isEmpty()) {
    // Label noCartsLabel = new Label("No carts in history for " + enteredName);
    // noCartsLabel.setStyle("-fx-text-fill: white;" + "-fx-font-weight: bold;" +
    // "-fx-font-size: 20;");
    // root.getChildren().addAll(welcomeLabel, noCartsLabel);
    // } else {
    // root.getChildren().addAll(welcomeLabel, tableView);

    // }
    // Customer.currentCart.clear();

    /*********************************************************************
     * *****************************************************************
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

    private Button createStyledButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setStyle(
                "-fx-background-color: #615BA2; -fx-text-fill: white; -fx-font-size: 20; -fx-pref-width: 200; -fx-pref-height: 50; -fx-background-radius: 25;");
        return button;
    }

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

    // Current cart
    private Button createCurrentcart() {
        Button currentCartButton = new Button("");

        ImageView cartIcon = new ImageView(new Image(cartIconPath));
        cartIcon.setFitWidth(60);
        cartIcon.setFitHeight(60);
        cartIcon.setPreserveRatio(true);
        currentCartButton.setGraphic(cartIcon);
        currentCartButton.setContentDisplay(ContentDisplay.CENTER);
        // css circle button
        currentCartButton.setStyle(
                "-fx-shape: \"M 15 15 A 15 15 0 1 0 15.001 15 Z\"; -fx-alignment: center;-fx-background-color: rgba(100, 100, 100, 0.2);");

        return currentCartButton;
    }

    private VBox createCartPanel() {
        VBox Cpanel = new VBox(10);
        Cpanel.setStyle("-fx-background-color: rgba(255, 255, 255, 1);"); // (1)opaque white
        VBox cartFieldsScene = createCartFieldsScene();
        Cpanel.getChildren().add(cartFieldsScene);
        Cpanel.setAlignment(Pos.CENTER);
        return Cpanel;
    }

    private void slideCartPanel() {
        if (cartPanel.getTranslateX() == -510) {
            // If cart panel is out, hide it
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), cartPanel);
            tt.setToX(-cartPanel.getWidth());
            tt.setOnFinished(e -> {
            });
            tt.play();
        } else // show cart panel
        {
            TranslateTransition ttt = new TranslateTransition(Duration.seconds(1), cartPanel);
            boolean isVisible = cartPanel.getTranslateX() == 0;
            double targetTranslation = isVisible ? -cartPanel.getWidth() : -510;
            ttt.setToX(targetTranslation);
            ttt.play();
        }
    }

    // RATING STARS
    private HBox createRatingStars() {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);

        for (int i = 0; i < NUM_STARS; i++) {
            Polygon star = createStar();

            star.setOnMouseClicked(event -> toggleStar(star));

            hbox.getChildren().add(star);
        }

        return hbox;
    }

    private void toggleStar(Polygon star) {
        if (star.getFill() == Color.TRANSPARENT) {
            star.setFill(Color.YELLOW);
            cArt.Rating++;
        } else {
            star.setFill(Color.TRANSPARENT);
            cArt.Rating--;
        }
        System.out.println("Clicked Stars: " + cArt.Rating);
    }

    private Polygon createStar() {
        double scalingFactor = 0.4;

        double[] starPoints = {
                0, 3,
                // x y
                10 * scalingFactor, 37 * scalingFactor, // 2
                40 * scalingFactor, 40 * scalingFactor, // 3
                20 * scalingFactor, 60 * scalingFactor, // 4
                30 * scalingFactor, 90 * scalingFactor, // 5
                0, 75 * scalingFactor,
                -30 * scalingFactor, 90 * scalingFactor, // 5m
                -20 * scalingFactor, 60 * scalingFactor, // 4m
                -40 * scalingFactor, 40 * scalingFactor, // 3m
                -10 * scalingFactor, 37 * scalingFactor// 2m

        };

        Polygon star = new Polygon(starPoints);
        star.setFill(Color.TRANSPARENT);
        star.setStroke(Color.BLACK);

        return star;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
