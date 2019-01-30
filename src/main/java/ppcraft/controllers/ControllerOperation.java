package ppcraft.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ppcraft.objects.Site;
import ppcraft.operations.Check;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static ppcraft.main.Main.*;

public class ControllerOperation implements Initializable {
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

    private ResourceBundle resourceBundle;
    private FXMLLoader fxmlLoader = new FXMLLoader();

    public void ok(ActionEvent actionEvent) {
        if (address.getText().equals("")){
            errorMessage(fxmlLoader.getResources().getString("error_not_site"));
        }else if(login.getText().equals("")) {
            errorMessage(fxmlLoader.getResources().getString("error_not_login"));
        }else if (password.getText().equals("")) {
            errorMessage(fxmlLoader.getResources().getString("error_not_password"));
        }else if (Check.checkAddress(siteList,address.getText()) < siteList.size()){
            if (!idButton.equals(String.valueOf(Check.checkAddress(siteList,address.getText())))){
                errorMessage(address.getText() + fxmlLoader.getResources().getString("error_exist"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        fxmlLoader.setResources(ResourceBundle.getBundle(LOCALEPATH, LOCALLANG));
    }

    private void writeInSite(ActionEvent actionEvent){
        site.setAddress(address.getText().toLowerCase());
        site.setLogin(login.getText());
        site.setPassword(password.getText());
        cancel(actionEvent);
    }

    private void errorMessage(String textError){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(fxmlLoader.getResources().getString("error_operation"));
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(textError);
        alert.showAndWait();
    }

    public void setSiteList(List<Site> siteList) {
        this.siteList = siteList;
    }
}
