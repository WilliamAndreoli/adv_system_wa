package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
	
	@FXML
	public void onMenuItemHelpAction() {
		System.out.println("onMenuItemHelpAction");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	@FXML
	public void onBtRegistrarAction() {
		System.out.println("onBtRegistrarAction");
	}
	
	@FXML
	public void onBtLoginAction() {
		System.out.println("onBtLoginAction");
	}
	
	@FXML
	public void onBtSairAction() {
		System.out.println("onBtSairAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	
		
	}
	
	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	
	
}
