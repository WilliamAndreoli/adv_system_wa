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
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.Advogado;
import model.entities.Cliente;
import model.entities.Parte_Processo;
import model.entities.Processo;
import model.entities.Tribunal;
import model.exceptions.ValidationException;
import model.services.AdvogadoService;
import model.services.ClienteService;
import model.services.Parte_ProcessoService;
import model.services.ProcessosService;
import model.util.Status;

public class ProcessoFormController implements Initializable {

	private Processo entity;

	private ProcessosService processosService;

	private ClienteService clienteService;

	private AdvogadoService advogadoService;

	private Parte_ProcessoService parte_ProcessoService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtNumero_Processo;

	@FXML
	private DatePicker dpData_De_Abertura;

	@FXML
	private TextField txtTipo;

	@FXML
	private ComboBox<Status> comboBoxStatus_Processo;

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

	private ObservableList<Cliente> obsList;

	public void setProcesso(Processo entity) {
		this.entity = entity;
	}

	public void setServices(ProcessosService processosService, ClienteService clienteService,
			AdvogadoService advogadoService, Parte_ProcessoService parte_ProcessoService) {
		this.processosService = processosService;
		this.clienteService = clienteService;
		this.advogadoService = advogadoService;
		this.parte_ProcessoService = parte_ProcessoService;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	private void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (processosService == null) {
			throw new IllegalStateException("Service was null");
		}

		try {
			entity = getFormData();
			processosService.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}

		System.out.println("onBtSalvarAction");
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	private Processo getFormData() {
		Processo obj = new Processo();

		ValidationException exception = new ValidationException("Validation error");

		if (txtNumero_Processo.getText() == null || txtNumero_Processo.getText().trim().equals("")) {
			exception.addError("numero_Processo", "field can't be empty");
		}

		obj.setNumero_Processo(txtNumero_Processo.getText());

		if (dpData_De_Abertura.getValue() == null) {
			exception.addError("data_De_Abertura", "field can't be empty");
		} else {
			Instant instant = Instant.from(dpData_De_Abertura.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setData_De_Abertura(Date.from(instant));
		}

		obj.setTipo(txtTipo.getText());
		obj.setStatus(comboBoxStatus_Processo.getValue());
		obj.setJuiz(txtJuiz.getText());
		obj.setDescricao(txtDescricao.getText());
		obj.setHonorarios(Utils.tryParseToDouble(txtHonorarios.getText()));
		obj.setCustos(Utils.tryParseToDouble(txtCustos.getText()));
		obj.setCliente_Id(comboBoxCliente.getValue());
		obj.setAdvogado_Id(comboBoxAdvogado.getValue());
		obj.setPartes(comboBoxParte.getValue());
		obj.setTribunal(comboBoxTribunal.getValue());
		obj.setUsuario(obj.getUsuario());
		return null;
	}

	@FXML
	private void onBtCancelAction() {
		Stage stage = (Stage) btCancel.getScene().getWindow();
		stage.close();
	}

	public void updateFormData() {
		txtNumero_Processo.setText(entity.getNumero_Processo());
		Locale.setDefault(Locale.US);
		if (entity.getData_De_Abertura() != null) {
			dpData_De_Abertura
					.setValue(LocalDate.ofInstant(entity.getData_De_Abertura().toInstant(), ZoneId.systemDefault()));
		}
		txtTipo.setText(entity.getTipo());

		if (entity.getStatus() == null) {
			comboBoxStatus_Processo.getSelectionModel().selectFirst();
		} else {
			comboBoxStatus_Processo.setValue(entity.getStatus());
		}

		txtJuiz.setText(entity.getJuiz());
		txtDescricao.setText(entity.getDescricao());
		txtHonorarios.setText(String.valueOf(entity.getHonorarios()));
		txtCustos.setText(String.valueOf(entity.getCustos()));

		if (entity.getCliente_Id() == null) {
			comboBoxCliente.getSelectionModel().selectFirst();
		} else {
			comboBoxCliente.setValue(entity.getCliente_Id());
		}

		if (entity.getAdvogado_Id() == null) {
			comboBoxAdvogado.getSelectionModel().selectFirst();
		} else {
			comboBoxAdvogado.setValue(entity.getAdvogado_Id());
		}

		if (entity.getPartes() == null) {
			comboBoxParte.getSelectionModel().selectFirst();
		} else {
			comboBoxParte.setValue(entity.getPartes());
		}

		if (entity.getTribunal() == null) {
			comboBoxTribunal.getSelectionModel().selectFirst();
		} else {
			comboBoxTribunal.setValue(entity.getTribunal());
		}
	}

	public void loadAssociatedObjects() {
		if (clienteService == null) {
			throw new IllegalStateException("ClienteService was null");
		}
		if (advogadoService == null) {
			throw new IllegalStateException("AdvogadoService was null");
		}

		// Carregar clientes
		List<Cliente> clienteList = clienteService.findAll();
		ObservableList<Cliente> clienteObsList = FXCollections.observableArrayList(clienteList);
		comboBoxCliente.setItems(clienteObsList);

		// Carregar advogados
		List<Advogado> advogadoList = advogadoService.findAll();
		ObservableList<Advogado> advogadoObsList = FXCollections.observableArrayList(advogadoList);
		comboBoxAdvogado.setItems(advogadoObsList);

		// Carregar partes
		List<Parte_Processo> partesList = parte_ProcessoService.findAll();
		ObservableList<Parte_Processo> parteObsList = FXCollections.observableArrayList(partesList);
		comboBoxParte.setItems(parteObsList);

		// Carregar status
		ObservableList<Status> statusObsList = FXCollections.observableArrayList(Status.values());
		comboBoxStatus_Processo.setItems(statusObsList);
	}

	private void initializeComboBoxStatus() {
		Callback<ListView<Status>, ListCell<Status>> factory = lv -> new ListCell<Status>() {
			@Override
			protected void updateItem(Status item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.toString());
			}
		};
		comboBoxStatus_Processo.setCellFactory(factory);
		comboBoxStatus_Processo.setButtonCell(factory.call(null));
	}

	private void initializeComboBoxCliente() {
		Callback<ListView<Cliente>, ListCell<Cliente>> factory = lv -> new ListCell<Cliente>() {
			@Override
			protected void updateItem(Cliente item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxCliente.setCellFactory(factory);
		comboBoxCliente.setButtonCell(factory.call(null));
	}

	private void initializeComboBoxAdvogado() {
		Callback<ListView<Advogado>, ListCell<Advogado>> factory = lv -> new ListCell<Advogado>() {
			@Override
			protected void updateItem(Advogado item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxAdvogado.setCellFactory(factory);
		comboBoxAdvogado.setButtonCell(factory.call(null));
	}
	
	private void initializeComboBoxParte() {
		Callback<ListView<Parte_Processo>, ListCell<Parte_Processo>> factory = lv -> new ListCell<Parte_Processo>() {
			@Override
			protected void updateItem(Parte_Processo item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxParte.setCellFactory(factory);
		comboBoxParte.setButtonCell(factory.call(null));
	}

	@Override
	public void initialize(URL uri, ResourceBundle bd) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtNumero_Processo, 100);
		Constraints.setTextFieldMaxLength(txtTipo, 80);
		Constraints.setTextFieldMaxLength(txtJuiz, 80);
		Constraints.setTextAreaMaxLength(txtDescricao, 1000);
		Constraints.setTextFieldDouble(txtHonorarios);
		Constraints.setTextFieldDouble(txtCustos);
		initializeComboBoxCliente();
		initializeComboBoxStatus();
		initializeComboBoxAdvogado();
		initializeComboBoxParte();
	}

}
