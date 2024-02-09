package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MenuListController implements Initializable {

	@FXML
	private Button btProcessos;

	@FXML
	private Button btConsultas;

	@FXML
	private Button btClientes;

	@FXML
	public void onBtProcessosAction() {
		loadView("/gui/ProcessosList.fxml", x -> {});
	}

	@FXML
	public void onBtConsultasAction() {
		System.out.println("onBtConsultasAction");
	}

	@FXML
	public void onBtClientesAction() {
		System.out.println("onBtClientesAction");
	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			AnchorPane newAnchorPane = loader.load();
			replaceSceneContent(newAnchorPane);

			T controller = loader.getController();
			initializingAction.accept(controller);
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

	@Override
	public void initialize(URL uri, ResourceBundle bd) {

	}

}
