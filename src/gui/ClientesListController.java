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
import javafx.scene.control.TextField;
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
	private TextField textFieldPesquisa;

	@FXML
	private Button btNovo;

	@FXML
	private Button btVoltar;

	private ObservableList<Cliente> obsList;

	public void setClienteService(ClienteService service) {
		this.service = service;
	}

	@FXML
	public void obBtNovoAction() {
		openMenuClienteList();
	}

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

	private void openMenuClienteList() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MenuClienteList.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);

			Stage newStage = new Stage(); // Crie uma nova instância de Stage
			newStage.setScene(scene);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace(); // Trate a exceção de acordo com a sua lógica de tratamento de erros
		}
	}
	
	private void pesquisarCliente(String nome) {
	    if (service == null) {
	        throw new IllegalStateException("Service was null");
	    }
	    List<Cliente> list;
	    if (nome == null || nome.isEmpty()) {
	        list = service.findAll(); // Se a pesquisa estiver vazia, exiba todos os clientes
	    } else {
	        list = service.findByNome(nome); // Implemente este método em seu serviço para buscar clientes pelo nome
	    }
	    obsList = FXCollections.observableArrayList(list);
	    tableViewClientes.setItems(obsList);
	}

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
		
		textFieldPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
	        pesquisarCliente(newValue);
	    });
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		pesquisarCliente(textFieldPesquisa.getText()); // Atualize a tabela considerando o filtro de pesquisa atual
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
