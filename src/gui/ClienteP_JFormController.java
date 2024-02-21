package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Pessoa_Juridica;
import model.exceptions.ValidationException;
import model.services.Pessoa_JuridicaService;

public class ClienteP_JFormController implements Initializable {

	private Pessoa_JuridicaService service;

	private Pessoa_Juridica entity;

	private Cliente cliente;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtNome_Fantasia;

	@FXML
	private TextField txtCnpj;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancel;

	@FXML
	private Label labelError;

	public void setPessoa_JuridicaService(Pessoa_JuridicaService service) {
		this.service = service;
	}

	public void setPessoa_Juridica(Pessoa_Juridica entity) {
		this.entity = entity;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@FXML
	private void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity Pessoa_Juridica was null");
		}
		if (cliente == null) {
			throw new IllegalStateException("Entity Cliente was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		try {
			cliente = getFormClienteData();
			entity = getFormP_FData();
			service.saveOrUpdate(entity, cliente);
			
			if (entity != null) {
				Alerts.showAlert("Registrado!", null, "Cliente pessoa jurídica salvo com sucesso!", AlertType.CONFIRMATION);
			}
			
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	private void onBtCancelAction() {
		Stage stage = (Stage) btCancel.getScene().getWindow();
		stage.close();
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	private Pessoa_Juridica getFormP_FData() {
		Pessoa_Juridica obj = new Pessoa_Juridica();

		ValidationException exception = new ValidationException("Validation error");

		if (txtCnpj.getText() == null || txtCnpj.getText().trim().equals("")) {
			exception.addError("cpf", "field can't be empty");
		}

		if (txtNome_Fantasia.getText() == null || txtNome_Fantasia.getText().trim().equals("")) {
			exception.addError("rg", "field can't be empty");
		}

		obj.setCnpj(txtCnpj.getText());
		obj.setNome_Fantasia(txtNome_Fantasia.getText());
		
		return obj;
	}
	
	private Cliente getFormClienteData() {
		Cliente cliente = new Cliente();

		ValidationException exception = new ValidationException("Validation error");

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "field can't be empty");
		}

		cliente.setNome(txtNome.getText());
		cliente.setEmail(txtEmail.getText());
		cliente.setTelefone(txtTelefone.getText());
		cliente.setTipo("P_F");

		return cliente;
	}

	public void updateFormData() {
		txtNome.setText(cliente.getNome());
		txtEmail.setText(cliente.getEmail());
		txtTelefone.setText(cliente.getTelefone());

		txtCnpj.setText(entity.getCnpj());
		txtNome_Fantasia.setText(entity.getNome_Fantasia());
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL uri, ResourceBundle bd) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtNome, 80);
		Constraints.setTextFieldMaxLength(txtEmail, 80);
		Constraints.setTextFieldMaxLength(txtTelefone, 50);
		Constraints.setTextFieldMaxLength(txtCnpj, 14);
		Constraints.setTextFieldMaxLength(txtNome_Fantasia, 80);
	}

}
