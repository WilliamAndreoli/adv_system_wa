package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.entities.Advogado;
import model.entities.Cliente;
import model.entities.Parte_Processo;
import model.entities.Processo;
import model.entities.Tribunal;
import model.entities.Usuario;
import model.services.AdvogadoService;
import model.services.ClienteService;
import model.services.ProcessosService;
import model.util.Status;

public class ProcessosListController implements Initializable, DataChangeListener {

	private ProcessosService service;

	@FXML
	private VBox vBox;

	@FXML
	private TableView<Processo> tableViewProcesso;

	@FXML
	private TableColumn<Processo, Integer> tableColumnId;

	@FXML
	private TableColumn<Processo, String> tableColumnNumero;

	@FXML
	private TableColumn<Processo, Date> tableColumnDataAbertura;

	@FXML
	private TableColumn<Processo, String> tableColumnTipo;

	@FXML
	private TableColumn<Processo, Status> tableColumnStatus;

	@FXML
	private TableColumn<Processo, String> tableColumnJuiz;

	@FXML
	private TableColumn<Processo, String> tableColumnDescricao;

	@FXML
	private TableColumn<Processo, Double> tableColumnHonorarios;

	@FXML
	private TableColumn<Processo, Double> tableColumnCustos;

	@FXML
	private TableColumn<Processo, Cliente> tableColumnCliente;

	@FXML
	private TableColumn<Processo, Advogado> tableColumnAdvogado;

	@FXML
	private TableColumn<Processo, Parte_Processo> tableColumnParte;

	@FXML
	private TableColumn<Processo, Tribunal> tableColumnTribunal;

	@FXML
	private TableColumn<Processo, Usuario> tableColumnUsuario;

	@FXML
	private Button btNovo;

	private ObservableList<Processo> obsList;

	public void setProcessosService(ProcessosService service) {
		this.service = service;
	}

	@FXML
	public void obBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Processo obj = new Processo();
		createDialogForm("/gui/ProcessoForm.fxml", obj, parentStage);
	}

	private void createDialogForm(String absoluteName, Processo obj, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			AnchorPane pane = loader.load();

			// Criar uma nova cena e definir o pane como sua raiz
			Scene cena = new Scene(pane);

			ProcessoFormController controller = loader.getController();
			controller.setProcesso(obj);
			controller.setServices(new ProcessosService(), new ClienteService(), new AdvogadoService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Processo Data");
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
	public void initialize(URL uri, ResourceBundle bd) {
		initializeNodes();

	}

	private void initializeNodes() {
		System.out.println("initializeNodes() chamado.");
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNumero.setCellValueFactory(new PropertyValueFactory<>("numero_Processo"));
		Utils.formatTableColumnDate(tableColumnDataAbertura, "dd/MM/yyyy");
		tableColumnDataAbertura.setCellValueFactory(new PropertyValueFactory<>("data_De_Abertura"));
		tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		//tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status_Processo"));
		tableColumnJuiz.setCellValueFactory(new PropertyValueFactory<>("juiz"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnHonorarios.setCellValueFactory(new PropertyValueFactory<>("honorarios"));
		Utils.formatTableColumnDouble(tableColumnHonorarios, 2);
		tableColumnCustos.setCellValueFactory(new PropertyValueFactory<>("custos"));
		Utils.formatTableColumnDouble(tableColumnCustos, 2);
		tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente_Id"));
		tableColumnAdvogado.setCellValueFactory(new PropertyValueFactory<>("advogado_Id"));
		tableColumnParte.setCellValueFactory(new PropertyValueFactory<>("partes"));
		tableColumnTribunal.setCellValueFactory(new PropertyValueFactory<>("tribunal"));
		tableColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

		Platform.runLater(() -> {
			Scene scene = tableViewProcesso.getScene();
			if (scene != null) {
				Window window = scene.getWindow();
				if (window instanceof Stage) {
					Stage stage = (Stage) window;
					vBox.prefHeightProperty().bind(stage.heightProperty());
					vBox.prefWidthProperty().bind(stage.widthProperty());
					tableViewProcesso.prefHeightProperty().bind(stage.heightProperty());
					tableViewProcesso.prefWidthProperty().bind(stage.widthProperty());
				} else {
					System.out.println("A janela atual não é um Stage.");
				}
			} else {
				System.out.println("A cena ainda não está carregada.");
			}
		});
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Processo> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewProcesso.setItems(obsList);
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
