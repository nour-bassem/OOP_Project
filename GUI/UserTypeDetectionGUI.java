import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UserTypeDetectionGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage primaryStage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("User Type Detection");

        // Create three buttons
        Button Badmin = createStyledButton("Admin");
        Button Bcashier = createStyledButton("Cashier");
        Button Bcustomer = createStyledButton("Customer");

        VBox vbox = new VBox(10, Badmin, Bcashier, Bcustomer);
        vbox.setAlignment(Pos.CENTER);

        // Set the background with the image
        setBackgroundImage(vbox);

        Badmin.setOnAction(e -> handleUserTypeSelection("Admin"));
        Bcashier.setOnAction(e -> handleUserTypeSelection("Cashier"));
        Bcustomer.setOnAction(e -> handleUserTypeSelection("Customer"));
        primaryStage.setWidth(800);
        primaryStage.setHeight(450);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private Button createStyledButton(String userType) {
        Button button = new Button(userType);
        button.setStyle("-fx-background-color: #615BA2; -fx-text-fill: white; -fx-font-size: 20; " +
                "-fx-pref-width: 200; -fx-pref-height: 50; -fx-background-radius: 25;");
        return button;
    }

    private void setBackgroundImage(VBox vbox) {
        Screen screen = Screen.getPrimary();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:C:/Users/shex/Downloads/pexels-codioful-(formerly-gradienta)-7130470.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight(),
                        false, false, false, true));
        Background background = new Background(backgroundImage);
        vbox.setBackground(background);
    }

    private void handleUserTypeSelection(String userType) {
        if ("Admin".equals(userType)) {
            AdminGUI adminGUI = new AdminGUI();
            adminGUI.start(new Stage());
            primaryStage.close();
        } else if ("Cashier".equals(userType)) {
            CashierGUI cGui = new CashierGUI();
            cGui.start(new Stage());
            primaryStage.close();
        } else if ("Customer".equals(userType)) {
            CustomerGUI custgui = new CustomerGUI();
            custgui.start(new Stage());
        }
    }
}
