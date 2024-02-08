package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UsuarioFormController implements Initializable {

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	
	@FXML
    private void onBtSalvarAction() {
        System.out.println("onBtSalvarAction");
    }
	
	@FXML
    private void onBtCancelAction() {
        System.out.println("onBtCancelAction");
    }
	
}
