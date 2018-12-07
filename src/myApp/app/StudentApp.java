package myApp.app;

import javafx.application.Application;
import javafx.stage.Stage;
import myApp.ui.ClientLoginUI;

public class StudentApp extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//call the login stage as a primary stage for student management application
		ClientLoginUI clientLoginStage = new ClientLoginUI();
		clientLoginStage.studentLogin();
	}
	
	//launch the application
	public static void main (String[] args) {
		Application.launch(args);
	}
}
