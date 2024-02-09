package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Usuario;
import model.services.UsuarioService;

public class MainViewController implements Initializable {

	private UsuarioService userService;

	@FXML
	private ScrollPane rootPane;

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
	
	private void setUsuarioService(UsuarioService service) {
		this.userService = service;
	}

	@FXML
	private void onMenuItemHelpAction() {
		loadView("/gui/Help.fxml", x -> {
		});
	}

	@FXML
	private void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {
		});
	}

	@FXML
	private void onBtRegistrarAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Usuario obj = new Usuario();
		createDialogForm("/gui/UsuarioForm.fxml", obj, parentStage);
	}

	@FXML
	private void onBtLoginAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Usuario obj = new Usuario();
		createDialogForm2("/gui/LoginForm.fxml", obj, parentStage);
	}

	@FXML
	private void onBtSairAction(ActionEvent event) {
		Platform.exit();
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

	private <T> void createDialogForm(String absoluteName, Usuario obj, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			AnchorPane pane = loader.load();

			// Criar uma nova cena e definir o pane como sua raiz
			Scene cena = new Scene(pane);

			UsuarioFormController controller = loader.getController();
			controller.setUsuario(obj);
			controller.setUsuarioService(new UsuarioService());

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Usuario Data");
			dialogStage.setScene(cena);
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			// Lidar com a exceção
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	private <T> void createDialogForm2(String absoluteName, Usuario obj, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			AnchorPane pane = loader.load();

			// Criar uma nova cena e definir o pane como sua raiz
			Scene cena = new Scene(pane);

			LoginFormController controller = loader.getController();
			controller.setUsuario(obj);
			controller.setUsuarioService(new UsuarioService());

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Login Data");
			dialogStage.setScene(cena);
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			// Lidar com a exceção
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// Verifica se a cena do rootPane não é nula
		if (rootPane.getScene() != null) {
			// Obtém o palco associado à cena
			Stage stage = (Stage) rootPane.getScene().getWindow();
			// Define o evento onHiding para encerrar a aplicação ao fechar a janela
			stage.setOnHiding(event -> {
				Platform.exit(); // Encerra a aplicação
			});
		}
	}
}