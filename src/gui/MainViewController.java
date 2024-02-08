package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.services.UsuarioService;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemHelp;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private Button btRegistrar;

    @FXML
    private Button btLogin;

    @FXML
    private Button btSair;

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    }

    @FXML
    private void onMenuItemHelpAction() {
        loadView("/gui/Help.fxml");
    }

    @FXML
    private void onMenuItemAboutAction() {
        loadView("/gui/About.fxml");
    }

    @FXML
    private void onBtRegistrarAction(ActionEvent event) {
        loadView("/gui/UsuarioForm.fxml");
    }

    @FXML
    private void onBtLoginAction() {
        loadView("/gui/LoginForm.fxml");
    }

    @FXML
    private void onBtSairAction(ActionEvent event) {
        Platform.exit();
    }

    private synchronized void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            AnchorPane newAnchorPane = loader.load();
            replaceSceneContent(newAnchorPane);
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
    
    private synchronized void loadView2(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            AnchorPane newAnchorPane = loader.load();
            replaceSceneContent(newAnchorPane);
            
            UsuarioFormController controller = loader.getController();
            controller.setUsuarioService(new UsuarioService());
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    private void replaceSceneContent(AnchorPane newAnchorPane) {
        Scene mainScene = Main.getMainScene();
        ScrollPane scrollPane = (ScrollPane) mainScene.getRoot();
        VBox mainVBox = (VBox) scrollPane.getContent();
        mainVBox.getChildren().clear();
        mainVBox.getChildren().add(newAnchorPane);
    }
}