package ppcraft.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import ppcraft.crypto.Crypto;
import ppcraft.impls.CollectionSitesDirectory;
import ppcraft.objects.Site;
import ppcraft.operations.Check;
import ppcraft.operations.PrepareData;
import ppcraft.operations.ReadFile;
import ppcraft.operations.WriteFile;

import java.io.*;

import static ppcraft.main.Main.*;

public class ControllerMain {

    private CollectionSitesDirectory sitesDirectoryImpl = new CollectionSitesDirectory();

    private PrepareData prepareData = new PrepareData();

    private  Stage mainStage;
    @FXML
    private Button chooseFile;
    @FXML
    private Button clearFile;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private TextField textSearch;
    @FXML
    private Button search;
    @FXML
    private TableView tableSitesDirectory;
    @FXML
    private TableColumn<Site, String> address;
    @FXML
    private TableColumn<Site, String> login;
    @FXML
    private TableColumn<Site, String> password;
    @FXML
    private Label info;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private ControllerOperation controllerOperation;
    private Stage operationStage;

    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Password File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            while (sitesDirectoryImpl.getSiteList().size() != 0){
                sitesDirectoryImpl.delete(sitesDirectoryImpl.getSiteList().get(0));
            }
            path = String.valueOf(file);
            ReadFile.read();
            info.setText("Выбран файл: " + path);
            boolean check = Check.checkFile(resurs[0]);
            try {
                Stage stage = new Stage();
                if (check == false){
                    stage.setTitle("Задайте новый пароль!");
                    idButton = "new";
                }else {
                    stage.setTitle("Введите пароль от файла!");
                    idButton = "checkPass";
                }
                Parent root = FXMLLoader.load(getClass().getResource(FXMLPASSWORD));
                stage.getIcons().add(new Image(ICO));
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
                stage.showAndWait();
                if (pass.equals("")){
                    info.setText("Файл с паролями не выбран!");
                }
                if (idButton.equals("oldPass") & resurs.length > 1){
                    for (int i = 1; i < resurs.length; i += 3){
                        if (!(prepareData.readSite(i)).getAddress().equals("")){
                            sitesDirectoryImpl.add(prepareData.readSite(i));
                        }

                    }
                }
            } catch (IOException e) {
                System.out.println("IOException :" + e);
            }
        }else {
            if (path.equals("")) {
                info.setText("Файл с паролями не выбран!");
            }
        }
    }

    public void clearFile(ActionEvent actionEvent) {
        if (!path.equals("") & !pass.equals("")){
            try {
                idButton = "clear";
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource(FXMLCLEAR));
                stage.setTitle("Удаление всех записей!");
                stage.getIcons().add(new Image(ICO));
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
                stage.showAndWait();
                if (idButton.equals("yes")){
                    String data = Crypto.encrypt(Crypto.md5Crypto()) + "\n";
                    WriteFile.write(data);
                    while (sitesDirectoryImpl.getSiteList().size() != 0){
                        sitesDirectoryImpl.delete(sitesDirectoryImpl.getSiteList().get(0));
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException :" + e);
            }
        }
    }

    public void add(ActionEvent actionEvent) {
        if (!path.equals("") & !pass.equals("")){
            controllerOperation.setSite(new Site());
            controllerOperation.setSiteList(sitesDirectoryImpl.getSiteList());
            Window parentWindow = ((Node)actionEvent.getSource()).getScene().getWindow();
            showEditDialog();
            if (controllerOperation.getSite().getAddress().equals("")){
                sitesDirectoryImpl.delete(controllerOperation.getSite());
            }else {
                sitesDirectoryImpl.add(controllerOperation.getSite());
                prepareData.writeOneSite(controllerOperation.getSite());
            }
        }
    }

    public void update(ActionEvent actionEvent) {
        if (!path.equals("") & !pass.equals("")){
            updateRun();
        }
    }

    public void delete(ActionEvent actionEvent) {
        if (!path.equals("") & !pass.equals("")){
            try {
                Site selectedSite = (Site) tableSitesDirectory.getSelectionModel().getSelectedItem();
                if (selectedSite != null){
                    idButton = "delete";
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource(FXMLCLEAR));
                    stage.setTitle("Удаление записи!");
                    stage.getIcons().add(new Image(ICO));
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
                    stage.showAndWait();
                    if (idButton.equals("yes")){
                        sitesDirectoryImpl.delete(selectedSite);
                        prepareData.writeSites(sitesDirectoryImpl.getSiteList());
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException :" + e);
            }
        }
    }

    public void search(ActionEvent actionEvent) {
        if (!pass.equals("")){
            String searchText = textSearch.getText();
            if (!searchText.equals("")){
                textSearch.setText("");
                int index = Check.checkAddress(sitesDirectoryImpl.getSiteList(),searchText);
                if (index < sitesDirectoryImpl.getSiteList().size()){
                    tableSitesDirectory.getSelectionModel().select(index);
                    controllerOperation.setSite((Site) tableSitesDirectory.getSelectionModel().getSelectedItem());
                }
            }
        }
    }

    private void showEditDialog(){
        if (operationStage==null){
            operationStage = new Stage();
            operationStage.setTitle("Изменение записи!");
            operationStage.getIcons().add(new Image(ICO));
            operationStage.setResizable(false);
            operationStage.setScene(new Scene(fxmlEdit));
            operationStage.initModality(Modality.WINDOW_MODAL);
            operationStage.initOwner(mainStage);
        }
        operationStage.showAndWait();
    }

    public void setMainStage(Stage mainStage){
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize() {
        address.setCellValueFactory(new PropertyValueFactory<Site, String>("address"));
        login.setCellValueFactory(new PropertyValueFactory<Site, String>("login"));
        password.setCellValueFactory(new PropertyValueFactory<Site, String>("password"));
        unitListeners();
        tableSitesDirectory.setItems(sitesDirectoryImpl.getSiteList());
        unitLoader();
    }

    private void unitListeners(){
        tableSitesDirectory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() ==2){
                    updateRun();
                }
            }
        });
    }

    private void updateRun(){
        Site selectedSite = (Site) tableSitesDirectory.getSelectionModel().getSelectedItem();
        idButton = String.valueOf(Check.checkAddress(sitesDirectoryImpl.getSiteList(), selectedSite.getAddress()));
        if (selectedSite != null){
            controllerOperation.setSite((Site) tableSitesDirectory.getSelectionModel().getSelectedItem());
            controllerOperation.setSiteList(sitesDirectoryImpl.getSiteList());
            showEditDialog();
            prepareData.writeSites(sitesDirectoryImpl.getSiteList());
        }
    }

    private void unitLoader(){
        try {
            fxmlLoader.setLocation(getClass().getResource(FXMLOPERATION));
            fxmlEdit = fxmlLoader.load();
            controllerOperation = fxmlLoader.getController();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
