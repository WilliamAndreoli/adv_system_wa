package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemHelp;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private Button btRegistrar;
	
	@FXML
	private Button btLogin;
	
	@FXML
	private Button btSair;
	
	@FXML
	public void onMenuItemHelpAction() {
		System.out.println("onMenuItemHelpAction");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("onMenuItemAboutAction");
	}
	
	@FXML
	public void onBtRegistrarAction() {
		System.out.println("onBtRegistrarAction");
	}
	
	@FXML
	public void onBtLoginAction() {
		System.out.println("onBtLoginAction");
	}
	
	@FXML
	public void onBtSairAction() {
		System.out.println("onBtSairAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

	
	
}
