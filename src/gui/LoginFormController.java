package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Usuario;
import model.services.UsuarioService;

public class LoginFormController implements Initializable {

	private UsuarioService service;
	
	private Usuario entity;
	
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
	
	public void setUsuarioService (UsuarioService service) {
		this.service = service;
	}
	
	public void setUsuario(Usuario entity) {
		this.entity = entity;
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
			System.out.println("Autenticado!");
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
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
	
	@Override
	public void initialize(URL uri, ResourceBundle bd) {
		
		
	}
	
}
