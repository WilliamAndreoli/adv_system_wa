package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import authentication.Session;
import db.DbException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Usuario;
import model.services.UsuarioService;

public class LoginFormController implements Initializable {

	private UsuarioService service;

	private Usuario entity;
	
	private Integer user_Id_Login;

	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtSenha;

	@FXML
	private Button btEntrar;

	@FXML
	private Button btCancel;

	@FXML
	private Label labelError;

	public void setUsuarioService(UsuarioService service) {
		this.service = service;
	}

	public void setUsuario(Usuario entity) {
		this.entity = entity;
	}
	
	public Integer getUser_Id_Login() {
		return user_Id_Login;
	}

	@FXML
	private void onBtEntrarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null!");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null!");
		}
		try {
			entity = getFormData();
			service.authenticateUser(entity);
			Usuario userAtual = service.findByLogin(entity.getLogin());
			
			onLoginSucess(userAtual.getId());

			loadView("/gui/MenuList.fxml", x -> {});

			// Fechar a janela de login atual
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
		}
	}

	public void onLoginSucess(Integer userId) {
		Session.setUserId(userId);
		System.out.println("Autenticado! id = " + userId);
	}
	
	private Usuario getFormData() {
		Usuario obj = new Usuario();

		obj.setLogin(txtLogin.getText());
		obj.setSenha(txtSenha.getText());

		return obj;
	}

	@FXML
	private void onBtCancelAction() {
		Stage stage = (Stage) btCancel.getScene().getWindow();
		stage.close();
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
