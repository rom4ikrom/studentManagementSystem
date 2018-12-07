package myApp.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import myApp.domain.Client;
import myApp.domain.LoginInfo;

public class LoginStageController {
	
	@FXML
	private AnchorPane root = new AnchorPane();

	@FXML
	private TextField txtLogin = new TextField();

	@FXML
	private PasswordField txtPass = new PasswordField();

	@FXML
	private Button btnLogin = new Button();

	@FXML
	private Button btnRegister = new Button();

	@FXML
	private Label lblError = new Label();
	
	@FXML
	private void accessAcc() throws IOException {
		String connectionType = "login";
		
		Stage stage = (Stage) root.getScene().getWindow();

		if((txtLogin.getText().trim().isEmpty() || txtLogin.getText() == null) ||
				txtPass.getText().trim().isEmpty() || txtPass.getText() == null) {
			lblError.setText("Please enter your login details.");

		} else {
			Client c = new Client();

			String username = txtLogin.getText().trim();
			String pass = txtPass.getText().trim();

			LoginInfo loginInfo = new LoginInfo(username, pass);
			String status = c.sendInfoToServer(connectionType, loginInfo);

			if(status.equals("found")) {
				ClientAccManUI clientAccountStage = new ClientAccManUI();
				clientAccountStage.studentAccount();
				stage.close();
			} else if(status.equals("adminUser")) {
				AdminSystemUI admin = new AdminSystemUI();
				admin.adminStage();
				stage.close();
			} else {
				lblError.setText("Invalid login information.");
				txtLogin.clear();
				txtPass.clear();

			}
		}
	}
	
	@FXML
	private void createAcc() {
		Stage stage = (Stage) root.getScene().getWindow();
		ClientRegisterUI clientRegisterStage = new ClientRegisterUI();
		clientRegisterStage.studentRegister();
		stage.close();
	}
	
	@FXML
	private void btnLoginMouseEnter() {
		btnLogin.setStyle("-fx-background-color: green;"
				+ "-fx-background-radius: 20px;"
				+ "-fx-text-fill: white;"
				+ "-fx-border-color: green;"
				+ "-fx-border-radius: 20px;"
				+ "-fx-border-width: 3px;");
	}
	
	@FXML
	private void btnLoginMouseExit() {
		btnLogin.setStyle("-fx-background-color: none;"
				+ "-fx-border-radius: 20px;"
				+ "-fx-border-width: 3px;"
				+ "-fx-border-color: green");
	}
	
	@FXML
	private void btnRegisterMouseEnter() {
		btnRegister.setStyle("-fx-underline: true;"
				+ "-fx-background-color: none");

	}
	
	@FXML
	private void btnRegisterMouseExit() {
		btnRegister.setStyle("-fx-underline: false;"
				+ "-fx-background-color: none");
	}
	
	@FXML
	private void rootMouseClick() {
		lblError.setText(null);
	}
}