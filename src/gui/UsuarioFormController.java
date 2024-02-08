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
import model.entities.Usuario;
import model.services.UsuarioService;

public class UsuarioFormController implements Initializable {

	private UsuarioService service;
	
	private Usuario entity;
	
	@FXML
	private TextField txtNovoLogin;
	
	@FXML
	private TextField txtNovaSenha;
	
	@FXML
	private Button btSalvar;
	
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
    private void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null!");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null!");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Usuario getFormData() {
		Usuario obj = new Usuario();
		
		obj.setLogin(txtNovoLogin.getText());
		obj.setSenha(txtNovaSenha.getText());
		
		return obj;
	}
	
	@FXML
    private void onBtCancelAction() {
        System.out.println("onBtCancelAction");
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
}
