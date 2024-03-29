package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Pessoa_Fisica;
import model.entities.Pessoa_Juridica;
import model.services.Pessoa_FisicaService;
import model.services.Pessoa_JuridicaService;

public class MenuClienteListController implements Initializable {

	@FXML
	private Button btP_F;

	@FXML
	private Button btP_J;

	@FXML
	private Button btVoltar;

	@FXML
	private void onBtP_FAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Cliente cliente = new Cliente();
		Pessoa_Fisica obj = new Pessoa_Fisica();
		createDialogFormP_F("/gui/ClienteP_FForm.fxml", cliente, obj, parentStage);
	}

	@FXML
	private void onBtP_JAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Cliente cliente = new Cliente();
		Pessoa_Juridica obj = new Pessoa_Juridica();
		createDialogFormP_J("/gui/ClienteP_JForm.fxml", cliente, obj, parentStage);
	}

	@FXML
	private void onBtVoltarAction() {
		Stage stage = (Stage) btVoltar.getScene().getWindow();
		stage.close();
	}

	private void createDialogFormP_F(String absoluteName, Cliente cliente, Pessoa_Fisica obj1, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			AnchorPane pane = loader.load();

			// Criar uma nova cena e definir o pane como sua raiz
			Scene cena = new Scene(pane);

			ClienteP_FFormController controller = loader.getController();
			controller.setCliente(cliente);
			controller.setPessoa_Fisica(obj1);
			controller.setPessoa_FisicaService(new Pessoa_FisicaService());
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Pessoa Física Data");
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
	
	private void createDialogFormP_J(String absoluteName, Cliente cliente, Pessoa_Juridica obj1, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			AnchorPane pane = loader.load();

			// Criar uma nova cena e definir o pane como sua raiz
			Scene cena = new Scene(pane);

			ClienteP_JFormController controller = loader.getController();
			controller.setCliente(cliente);
			controller.setPessoa_Juridica(obj1);
			controller.setPessoa_JuridicaService(new Pessoa_JuridicaService());
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Pessoa Física Data");
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
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

}
