package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.entities.Cliente;
import model.services.ClienteService;

public class ClientesListController implements Initializable, DataChangeListener {

	private ClienteService service;

	@FXML
	private VBox vBox;

	@FXML
	private TableView<Cliente> tableViewClientes;

	@FXML
	private TableColumn<Cliente, Integer> tableColumnId;

	@FXML
	private TableColumn<Cliente, String> tableColumnNome;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnTelefone;
	
	@FXML
	private TableColumn<Cliente, String> tableColumnTipo;

	@FXML
	private Button btNovo;
	
	@FXML
	private Button btVoltar;

	private ObservableList<Cliente> obsList;

	public void setClienteService(ClienteService service) {
		this.service = service;
	}

//	@FXML
//	public void obBtNovoAction(ActionEvent event) {
//		Stage parentStage = Utils.currentStage(event);
//		Cliente obj = new Cliente();
//		createDialogForm("/gui/ClienteForm.fxml", obj, parentStage);
//	}
	
	@FXML 
	public void onBtVoltarAction() {
		openMenuList();
	}
	
	private void openMenuList() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MenuList.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);

			Stage newStage = new Stage(); // Crie uma nova instância de Stage
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace(); // Trate a exceção de acordo com a sua lógica de tratamento de erros
		}
	}

//	private void createDialogForm(String absoluteName, Cliente obj, Stage parentStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			AnchorPane pane = loader.load();
//
//			// Criar uma nova cena e definir o pane como sua raiz
//			Scene cena = new Scene(pane);
//
//			ClienteFormController controller = loader.getController();
//			controller.setCliente(obj);
//			controller.setServices(new ClienteService());
//			controller.loadAssociatedObjects();
//			controller.subscribeDataChangeListener(this);
//			controller.updateFormData();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Enter Cliente Data");
//			dialogStage.setScene(cena);
//			dialogStage.setResizable(false);
//			dialogStage.initOwner(parentStage);
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.showAndWait();
//		} catch (IOException e) {
//			// Lidar com a exceção
//			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
//		}
//	}

	@Override
	public void initialize(URL uri, ResourceBundle bd) {
		initializeNodes();

	}

	private void initializeNodes() {
		System.out.println("initializeNodes() chamado.");
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		

		Platform.runLater(() -> {
			Scene scene = tableViewClientes.getScene();
			if (scene != null) {
				Window window = scene.getWindow();
				if (window instanceof Stage) {
					Stage stage = (Stage) window;
					vBox.prefHeightProperty().bind(stage.heightProperty());
					vBox.prefWidthProperty().bind(stage.widthProperty());
					tableViewClientes.prefHeightProperty().bind(stage.heightProperty());
					tableViewClientes.prefWidthProperty().bind(stage.widthProperty());
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
		List<Cliente> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClientes.setItems(obsList);
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
