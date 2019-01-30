package ppcraft.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static ppcraft.main.Main.*;

public class ControllerClear implements Initializable {

    @FXML
    private Label contentLabel;
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;

    private ResourceBundle resourceBundle;
    private FXMLLoader fxmlLoader = new FXMLLoader();

    public void yes(ActionEvent actionEvent) {
            idButton = "yes";
            no(actionEvent);
    }

    public void no(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        fxmlLoader.setResources(ResourceBundle.getBundle(LOCALEPATH, LOCALLANG));
        if ("clear".equals(idButton)) {
            contentLabel.setText(fxmlLoader.getResources().getString("label_clear_all"));
        } else if ("delete".equals(idButton)) {
            contentLabel.setText(fxmlLoader.getResources().getString("label_clear_one"));
        }
    }
}
