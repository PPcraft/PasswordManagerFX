package ppcraft.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import ppcraft.crypto.Crypto;
import ppcraft.operations.WriteFile;

import static ppcraft.main.Main.*;

public class ControllerPassword {
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button OkBtn;
    @FXML
    private Button cancelBtn;

    private String cryptoPass;



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

    @FXML
    private void initialize() {
        pass = "";

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
            alert.setTitle("Пароль введен не верно!");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Пароль введен не верно!");
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
