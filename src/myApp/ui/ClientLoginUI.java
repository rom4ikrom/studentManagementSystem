package myApp.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class ClientLoginUI {
	
	private Stage stage = new Stage();
	
	public void studentLogin() {
		
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("LoginStage.fxml"));
			root.setStyle("-fx-background-image:url(resources/loginStage.jpg)");
			Scene scene = new Scene(root,600,700);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
