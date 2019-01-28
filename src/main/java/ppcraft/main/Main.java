package ppcraft.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ppcraft.controllers.ControllerMain;

public class Main extends Application {
    public static final String ICO = "images/ico.png";
    public static final String FXMLMAIN = "/fxml/main.fxml";
    public static final String FXMLCLEAR = "/fxml/clearDialog.fxml";
    public static final String FXMLOPERATION = "/fxml/operationDialog.fxml";
    public static final String FXMLPASSWORD = "/fxml/passwordDialog.fxml";

    public static String pass = "";
    public static String path = "";
    public static String idButton = "";
    public static String[] resurs;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXMLMAIN));
        Parent fxmlMain = fxmlLoader.load();
        ControllerMain controllerMain = fxmlLoader.getController();
        controllerMain.setMainStage(primaryStage);
        primaryStage.getIcons().add(new Image(ICO));
        primaryStage.setTitle("Password Manager");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(fxmlMain, 700, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
