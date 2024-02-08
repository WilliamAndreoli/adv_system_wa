package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private void onBtSalvarAction() {
        System.out.println("onBtSalvarAction");
    }
	
	@FXML
    private void onBtCancelAction() {
        System.out.println("onBtCancelAction");
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		Usuario usuario = new Usuario();
		txtNovoLogin.setText(entity.getLogin());
		txtNovaSenha.setText(entity.getSenha());
	}
	
}
