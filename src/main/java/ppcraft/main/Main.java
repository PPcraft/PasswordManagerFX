package ppcraft.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ppcraft.controllers.ControllerMain;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class Main extends Application{
    public static final String ICO = "images/ico.png";
    public static final String FXMLMAIN = "/fxml/main.fxml";
    public static final String FXMLCLEAR = "/fxml/clearDialog.fxml";
    public static final String FXMLOPERATION = "/fxml/operationDialog.fxml";
    public static final String FXMLPASSWORD = "/fxml/passwordDialog.fxml";
    public static final String LOCALEPATH = "bundles.Locale";

    public static String pass = "";
    public static String path = "";
    public static String idButton = "";
    public static String[] resurs;
    public static Locale LOCALLANG;

    @Override
    public void start(Stage primaryStage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setAlertType(Alert.AlertType.NONE);
        alert.setHeaderText(null);
        alert.setContentText("Выберете язык/Choose language!");
        ButtonType buttonTypeRU = new ButtonType("Русский");
        ButtonType buttonTypeEN = new ButtonType("English");
        alert.getButtonTypes().setAll(buttonTypeRU, buttonTypeEN);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeRU){
            LOCALLANG = new Locale("ru");
        } else{
            LOCALLANG = new Locale("en");
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXMLMAIN));
        fxmlLoader.setResources(ResourceBundle.getBundle(LOCALEPATH, LOCALLANG));
        Parent fxmlMain = null;
        try {
            fxmlMain = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ControllerMain controllerMain = fxmlLoader.getController();
        controllerMain.setMainStage(primaryStage);
        primaryStage.getIcons().add(new Image(ICO));
        primaryStage.setTitle(fxmlLoader.getResources().getString("password_manager"));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(fxmlMain, 700, 400));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
