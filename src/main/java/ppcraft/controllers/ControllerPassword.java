package ppcraft.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import ppcraft.crypto.Crypto;
import ppcraft.operations.WriteFile;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static ppcraft.main.Main.*;

public class ControllerPassword implements Initializable {
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button OkBtn;
    @FXML
    private Button cancelBtn;

    private String cryptoPass;

    private ResourceBundle resourceBundle;
    private FXMLLoader fxmlLoader = new FXMLLoader();



    public void OkBtn(ActionEvent actionEvent) {
        pass = passwordText.getText();
        if (!pass.equals("")){
            this.cryptoPass = Crypto.encrypt(Crypto.md5Crypto());
            if ("new".equals(idButton)) {
                newPassword(actionEvent);
            } else if ("checkPass".equals(idButton)) {
                checkPassword(actionEvent);
            }
        }
    }

    public void cancelBtn(ActionEvent actionEvent) {
        if (pass.equals("")){
            path = "";
        }
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pass = "";
        this.resourceBundle = resources;
        fxmlLoader.setResources(ResourceBundle.getBundle(LOCALEPATH, LOCALLANG));
    }

    private void newPassword(ActionEvent actionEvent){
        String data = cryptoPass + "\n";
        WriteFile.write(data);
        cancelBtn(actionEvent);
    }

    private void checkPassword(ActionEvent actionEvent){
        String filePassword = resurs[0];
        if (filePassword.equals(cryptoPass)){
            idButton = "oldPass";
            cancelBtn(actionEvent);
        }else{
            pass = "";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle(fxmlLoader.getResources().getString("wrong_password"));
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(fxmlLoader.getResources().getString("wrong_password"));
            alert.showAndWait();
            passwordText.setText("");
        }
    }

    private EventHandler<WindowEvent> closeEventHandler = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
           path = "";
           pass = "";
        }
    };
}
