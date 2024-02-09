package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Advogado;
import model.entities.Cliente;
import model.entities.Parte_Processo;
import model.entities.Tribunal;
import model.util.Status;

public class ProcessoFormController implements Initializable {

	@FXML
	private TextField txtNumero_Processo;
	
	@FXML
	private DatePicker dpData_De_Abertura;
	
	@FXML
	private TextField txtTipo;
	
	@FXML
	private ComboBox<Status> comBoxStatus_Processo;
	
	@FXML
	private TextField txtJuiz;
	
	@FXML
	private TextArea txtDescricao;
	
	@FXML
	private TextField txtHonorarios;
	
	@FXML
	private TextField txtCustos;
	
	@FXML
	private ComboBox<Cliente> comboBoxCliente;
	
	@FXML
	private ComboBox<Advogado> comboBoxAdvogado;
	
	@FXML
	private ComboBox<Parte_Processo> comboBoxParte;
	
	@FXML
	private ComboBox<Tribunal> comboBoxTribunal;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private void onBtSalvarAction() {
		System.out.println("onBtSalvarAction");
	}
	
	@FXML
	private void onBtCancelAction() {
		Stage stage = (Stage) btCancel.getScene().getWindow();
		stage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
