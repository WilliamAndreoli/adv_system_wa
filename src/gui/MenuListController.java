package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.services.ProcessosService;

public class MenuListController implements Initializable {

	@FXML
	private VBox vBox;
	
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

			ProcessosListController controller = loader.getController();
			controller.setProcessosService(new ProcessosService());
			controller.updateTableView();
			
			T controller1 = loader.getController();
			initializingAction.accept(controller1);
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
		initializeNodes();
	}
	
	private void initializeNodes() {
		System.out.println("initializeNodes() chamado.");
		
		Platform.runLater(() -> {
		    Scene scene = btProcessos.getScene();
		    if (scene != null) {
		        Window window = scene.getWindow();
		        if (window instanceof Stage) {
		            Stage stage = (Stage) window;
		            vBox.prefHeightProperty().bind(stage.heightProperty());
		            vBox.prefWidthProperty().bind(stage.widthProperty());
		        } else {
		            System.out.println("A janela atual não é um Stage.");
		        }
		    } else {
		        System.out.println("A cena ainda não está carregada.");
		    }
		});
	}
}
