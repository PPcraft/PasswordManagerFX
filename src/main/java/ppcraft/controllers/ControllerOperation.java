package ppcraft.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ppcraft.objects.Site;
import ppcraft.operations.Check;

import static ppcraft.main.Main.idButton;

import java.util.List;

public class ControllerOperation {
    @FXML
    private TextField address;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;

    private Site site;
    private List<Site> siteList;

    public void ok(ActionEvent actionEvent) {
        if (address.getText().equals("")){
            errorMessage("Не введен адресс сайта!");
        }else if(login.getText().equals("")) {
            errorMessage("Не введен логин!");
        }else if (password.getText().equals("")) {
            errorMessage("Не введен пароль!");
        }else if (Check.checkAddress(siteList,address.getText()) < siteList.size()){
            if (!idButton.equals(String.valueOf(Check.checkAddress(siteList,address.getText())))){
                errorMessage("Сайт "+ address.getText() + " уже существует!");
            }else {
                writeInSite(actionEvent);
            }
        }else{
            writeInSite(actionEvent);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void setSite(Site site){
        if (site == null){
            return;
        }
        this.site = site;
        address.setText(site.getAddress());
        login.setText(site.getLogin());
        password.setText(site.getPassword());
    }

    public Site getSite(){
        return site;
    }

    @FXML
    private void initialize(){
    }

    private void writeInSite(ActionEvent actionEvent){
        site.setAddress(address.getText());
        site.setLogin(login.getText());
        site.setPassword(password.getText());
        cancel(actionEvent);
    }

    private void errorMessage(String textError){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Не все поля заполнены");
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(textError);
        alert.showAndWait();
    }

    public void setSiteList(List<Site> siteList) {
        this.siteList = siteList;
    }
}
