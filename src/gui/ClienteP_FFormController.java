package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Pessoa_Fisica;
import model.exceptions.ValidationException;
import model.services.Pessoa_FisicaService;

public class ClienteP_FFormController implements Initializable {

	private Pessoa_FisicaService service;

	private Pessoa_Fisica entity;

	private Cliente cliente;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtTipo;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtRg;

	@FXML
	private TextField txtCertidao_Casamento;

	@FXML
	private TextField txtCtps;

	@FXML
	private TextField txtCnh;

	@FXML
	private DatePicker dpData_Nascimento;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancel;

	@FXML
	private Label labelError;

	public void setPessoa_FisicaService(Pessoa_FisicaService service) {
		this.service = service;
	}

	public void setPessoa_Fisica(Pessoa_Fisica entity) {
		this.entity = entity;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@FXML
	private void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity Pessoa_Fisica was null");
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
				Alerts.showAlert("Registrado!", null, "Cliente pessoa física salvo com sucesso!", AlertType.CONFIRMATION);
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

	private Pessoa_Fisica getFormP_FData() {
		Pessoa_Fisica obj = new Pessoa_Fisica();

		ValidationException exception = new ValidationException("Validation error");

		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "field can't be empty");
		}

		if (txtRg.getText() == null || txtRg.getText().trim().equals("")) {
			exception.addError("rg", "field can't be empty");
		}

		obj.setCpf(txtCpf.getText());
		obj.setRg(txtRg.getText());
		obj.setCertidao_Casamento(txtCertidao_Casamento.getText());
		obj.setCtps(txtCtps.getText());
		obj.setCnh(txtCnh.getText());
		
		if (dpData_Nascimento.getValue() == null) {
			exception.addError("data_nascimento", "field can't be empty");
		} else {
			Instant instant = Instant.from(dpData_Nascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setData_Nascimento(Date.from(instant));
		}

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
		txtTipo.setText(cliente.getTipo());

		txtCpf.setText(entity.getCpf());
		txtRg.setText(entity.getRg());
		txtCertidao_Casamento.setText(entity.getCertidao_Casamento());
		txtCtps.setText(entity.getCtps());
		txtCnh.setText(entity.getCnh());

		Locale.setDefault(Locale.US);
		if (entity.getData_nascimento() != null) {
			dpData_Nascimento
					.setValue(LocalDate.ofInstant(entity.getData_nascimento().toInstant(), ZoneId.systemDefault()));
		}

	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL uri, ResourceBundle bd) {
		initializeNodes();
	}

	private void initializeNodes() {

	}

}
