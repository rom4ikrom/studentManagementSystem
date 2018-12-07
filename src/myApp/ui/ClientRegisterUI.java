package myApp.ui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import myApp.domain.Client;
import myApp.domain.Student;

public class ClientRegisterUI {
	
	private Label fName = new Label("First Name");
	private Label lName = new Label("Last Name");
	private Label tel = new Label("Telephone");
	private Label email = new Label("Email");
	private Label buildNo = new Label("Build No");
	private Label street = new Label("Street");
	private Label town = new Label("Town");
	private Label postcode = new Label("Postcode");
	
	private Label password = new Label("Password");
	
	private TextField txtFname = new TextField();
	private TextField txtLname = new TextField();
	private TextField txtTel = new TextField();
	private TextField txtEmail = new TextField();
	private TextField txtBuildNo = new TextField();
	private TextField txtStreet = new TextField();
	private TextField txtTown = new TextField();
	private TextField txtPostcode = new TextField();
	
	private PasswordField txtPass = new PasswordField();
	
	private Button btnRegister = new Button("Register");
	private Button btnLogin = new Button("Back to Login");
	
	private Label lblServerStudentID = new Label();
	private Label lblServerPassword = new Label();
	private Label lblError = new Label();
	
	private ArrayList<TextField> array = new ArrayList<TextField>();
	
	public void studentRegister() {
		Stage stage = new Stage();
		
		GridPane gPane = new GridPane();
		
		//first column
		gPane.add(fName, 0, 0);
		gPane.add(lName, 0, 1);
		gPane.add(tel, 0, 2);
		gPane.add(email, 0, 3);
		gPane.add(password, 0, 4);
		
		//second column
		gPane.add(txtFname, 1, 0);
		gPane.add(txtLname, 1, 1);
		gPane.add(txtTel, 1, 2);
		gPane.add(txtEmail, 1, 3);
		gPane.add(txtPass, 1, 4);
		
		//third column
		gPane.add(buildNo, 2, 0);
		gPane.add(street, 2, 1);
		gPane.add(town, 2, 2);
		gPane.add(postcode, 2, 3);

		//fourth column
		gPane.add(txtBuildNo, 3, 0);
		gPane.add(txtStreet, 3, 1);
		gPane.add(txtTown, 3, 2);
		gPane.add(txtPostcode, 3, 3);
		
		
		gPane.add(btnRegister, 0, 5);
		gPane.add(btnLogin, 0, 5);
		
		gPane.add(lblServerStudentID, 0, 6);
		gPane.add(lblServerPassword, 0, 7);
		
		gPane.add(lblError, 2, 4);
		
		lblError.setStyle(
				"-fx-text-fill: red;"
				+ "-fx-font-size: 12;");
		
		GridPane.setConstraints(lblServerStudentID, 0, 6, 4, 1);
		GridPane.setConstraints(lblServerPassword, 0, 7, 4, 1);
		GridPane.setConstraints(lblError, 2, 4, 2, 1);
		
		GridPane.setConstraints(btnRegister, 0, 5, 4, 1);
		GridPane.setHalignment(btnRegister, HPos.LEFT);
		//GridPane.setMargin(btnRegister, new Insets(20));
		
		GridPane.setConstraints(btnLogin, 0, 5, 4, 1);
		GridPane.setHalignment(btnLogin, HPos.RIGHT);
		//GridPane.setMargin(btnLogin, new Insets(20));
		
		gPane.setPadding(new Insets (50, 40, 50, 40));
		
		gPane.setVgap(10);
		gPane.setHgap(10);
		
		btnRegister.setOnAction(new InsertStudentInfo());
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			

			@Override
			public void handle(ActionEvent event) {
				ClientLoginUI clientLoginStage = new ClientLoginUI();
				clientLoginStage.studentLogin();
				stage.close();
			}
			
		});
		
		Scene scene = new Scene(gPane);
		stage.setScene(scene);
		stage.setTitle("Registration System");
		stage.show();
		
		array.add(txtFname);
		array.add(txtLname);
		array.add(txtTel);
		array.add(txtEmail);
		array.add(txtBuildNo);
		array.add(txtStreet);
		array.add(txtTown);
		array.add(txtPostcode);
		array.add(txtPass);
		
	}
	
	private class InsertStudentInfo implements EventHandler<ActionEvent> {;
		
		@Override
		public void handle(ActionEvent event) {
			
			int count = 0;
			String connnectionType = "registration";
			
			for(TextField tf : array) {
				if(!tf.getText().trim().isEmpty() && tf.getText() != null && count == 8
						&& txtLname.getText().trim().length() >= 3 && txtTel.getText().trim().length() >= 3) {
					String fname = txtFname.getText().trim();
					String lname = txtLname.getText().trim();
					String tel = txtTel.getText().trim();
					String email = txtEmail.getText().trim();
					String address = txtBuildNo.getText().trim() + " " + txtStreet.getText().trim() + ", " + txtTown.getText().trim();
					String postcode = txtPostcode.getText().trim();
					String password = txtPass.getText().trim();
					
					//send info to the server using method declared in Client class
					Client c = new Client();
					String id = " ";
					String course = " ";
					
					//create student object
					Student student = new Student(fname, lname, tel, email, address, postcode, id, password, course);
					
					String studentId = c.sendInfoToServer(connnectionType, student);
					
					lblServerStudentID.setText("Your student ID is: " + studentId);
					lblServerPassword.setText("Your password is: " + password);
					
					for(TextField t : array) {
						t.clear();
					}
					
					lblError.setText(null);
					
				} else if(count < 8 && !tf.getText().trim().isEmpty() && tf.getText() != null
						&& txtLname.getText().length() >= 3 && txtTel.getText().length() >= 3){
					count++;
					lblError.setText("Enter all details please.");
				} else {
					lblError.setText("Enter all details please.");
					break;
				}
			}
		}
	}
}
