package ppcraft.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static ppcraft.main.Main.idButton;

public class ControllerClear {

    @FXML
    private Label contentLabel;
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;

    public void yes(ActionEvent actionEvent) {
            idButton = "yes";
            no(actionEvent);
    }

    public void no(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize(){
        if ("clear".equals(idButton)) {
            contentLabel.setText("Вы действительно хотите очистить файл?");
        } else if ("delete".equals(idButton)) {
            contentLabel.setText("Вы действительно хотите удалить запись?");
        }
    }
}
