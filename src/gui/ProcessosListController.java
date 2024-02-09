package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.entities.Advogado;
import model.entities.Cliente;
import model.entities.Parte_Processo;
import model.entities.Processo;
import model.entities.Tribunal;
import model.entities.Usuario;
import model.services.ProcessosService;
import model.util.Status;

public class ProcessosListController implements Initializable {

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
	public void obBtNovoAction() {
		System.out.println("onBtNovoAction");
	}

	
	@Override
	public void initialize(URL uri, ResourceBundle bd) {
		initializeNodes();
		
	}


	private void initializeNodes() {
		System.out.println("initializeNodes() chamado.");
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNumero.setCellValueFactory(new PropertyValueFactory<>("numero_Processo"));
		tableColumnDataAbertura.setCellValueFactory(new PropertyValueFactory<>("data_De_Abertura"));
		tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnJuiz.setCellValueFactory(new PropertyValueFactory<>("juiz"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnHonorarios.setCellValueFactory(new PropertyValueFactory<>("honorarios"));
		tableColumnCustos.setCellValueFactory(new PropertyValueFactory<>("custos"));
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

}