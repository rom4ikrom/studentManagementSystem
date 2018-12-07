package myApp.ui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import myApp.domain.StudentTableViewHelper;
import myApp.domain.TimeTableViewHelper;
import myApp.domain.Timetable;
import myApp.domain.Client;
import myApp.domain.Student;

public class AdminSystemUI {

	private VBox root;
	
	private VBox studentRoot;
	private VBox courseRoot;

	private VBox studentBox;
	private GridPane studentPane;

	private VBox courseBox;
	private GridPane coursePane;
	
	private Label lblStatus;
	
	private TextField txtFname;
	private TextField txtLname;
	private TextField txtID;
	
	private TextField txtPass;
	private ComboBox<String> cbCourse;

	private TextField txtTel;
	private TextField txtEmail;
	private TextField txtAddress;
	private TextField txtPostcode;
	
	private TextField txtmn;
	private TextField txtRoom;
	private TextField txtDay;
	private TextField txtTime;
	private TextField txtLec;
	
	private TableView<Student> studentTable;
	private TableView<Timetable> moduleTable;

	public void adminStage() {

		Stage stage = new Stage();

		root = new VBox();

		// create menu
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(stage.widthProperty());
		
		//create account menu
		Menu account = new Menu("Account");
		MenuItem logOutAcc = new MenuItem("Log Out");
		MenuItem exitAcc = new MenuItem("Exit");

		// add action to log out option in account menu
		logOutAcc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientLoginUI clientLogin = new ClientLoginUI();
				clientLogin.studentLogin();
				stage.close();
			}
		});

		// add action to exit option in account menu
		exitAcc.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

		account.getItems().addAll(logOutAcc, exitAcc);
		
		//create view menu
		Menu view = new Menu("View");
		MenuItem viewStudentPane = new MenuItem("Manage Students");
		MenuItem viewTimetablePane = new MenuItem("Manage Timetable");

		view.getItems().addAll(viewStudentPane, viewTimetablePane);
		
		//add action to view student information
		viewStudentPane.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				//if else block checks if any pane is already took place at the scene
				if(root.getChildren().indexOf(studentRoot) == -1 && root.getChildren().indexOf(courseRoot) == -1) {
					studentRoot = manageStudent();
					root.getChildren().add(studentRoot);
				} else if(root.getChildren().indexOf(courseRoot) != -1 && root.getChildren().indexOf(studentRoot) == -1){
					root.getChildren().remove(courseRoot);
					studentRoot = manageStudent();
					root.getChildren().add(studentRoot);
				} else {
					root.getChildren().remove(studentRoot);
					root.getChildren().remove(courseRoot);
				}
			}
		});
		
		//add information to view timetable information
		viewTimetablePane.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				//if else block checks if any pane is already took place at the scene
				if(root.getChildren().indexOf(courseRoot) == -1 && root.getChildren().indexOf(studentRoot) == -1) {
					courseRoot = manageModule();
					root.getChildren().add(courseRoot);
				} else if(root.getChildren().indexOf(studentRoot) != -1 && root.getChildren().indexOf(courseRoot) == -1){
					root.getChildren().remove(studentRoot);
					courseRoot = manageModule();
					root.getChildren().add(courseRoot);
				} else {
					root.getChildren().remove(courseRoot);
					root.getChildren().remove(studentRoot);
				}
			}
		});
		
		menuBar.getMenus().addAll(account, view);
		
		//add menu to the root
		root.getChildren().addAll(menuBar);
		
		studentRoot = manageStudent();
		root.getChildren().add(studentRoot);
		
		//create scene and add it to stage
		Scene scene = new Scene(root, 1200, 700);
		stage.setTitle("Admin Management System");
		stage.setScene(scene);
		stage.show();
		
	}

	@SuppressWarnings("unchecked")
	private VBox manageStudent() {
		
		//vertical box and grid pane to create layout for student pane
		studentBox = new VBox();
		studentPane = new GridPane();
		
		FlowPane fPane = new FlowPane();
		
		//scroll pane for table view
		ScrollPane sp = new ScrollPane();
		sp.setPannable(true);

        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        
		//create labels
        Label title = new Label("Manage Student Information");
        
        title.setStyle(
        		"-fx-font-family: Montserrat;" +
				"-fx-font-size: 25;");
        title.setPadding(new Insets(0, 0, 10, 0));
        
		Label fName = new Label("First Name");
		Label lName = new Label("Last Name");
		Label id = new Label("Student ID");
		Label password = new Label("Password");
		Label course = new Label("Course");
		
		lblStatus = new Label();
		
		Label tel = new Label("Telephone");
		Label email = new Label("Email");
		Label address = new Label("Address");
		Label postcode = new Label("Postcode");
		
		//create text fields
		txtFname = new TextField();
		txtLname = new TextField();
		txtID = new TextField("none");
		//id cannot be edit as it is a primary key
		txtID.setEditable(false);
		txtID.setId("studentId");
		
		txtPass = new TextField();

		txtTel = new TextField();
		txtEmail = new TextField();
		txtAddress = new TextField();
		txtPostcode = new TextField();

		cbCourse = new ComboBox<String>();
		
		studentTable = new TableView<>();
		
		//add all text fields to the array list
		ArrayList<TextField> txtArray = new ArrayList<>();
		txtArray.add(txtFname);
		txtArray.add(txtLname);
		txtArray.add(txtID);
		txtArray.add(txtPass);
		txtArray.add(txtTel);
		txtArray.add(txtEmail);
		txtArray.add(txtAddress);
		txtArray.add(txtPostcode);
		
		//set action if mouse was clicked on student box
		studentBox.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				lblStatus.setText(null);
				studentTable.getSelectionModel().clearSelection();
				for(TextField tf : txtArray) {
					tf.clear();
					cbCourse.setValue(null);
				}
			}
		});
		
		// add text fields and combo box to the grid pane
		cbCourse.getItems().addAll(null, "Computer Science", "Creative Computing", "Cyber Security");
		
		studentPane.getChildren().add(title);
		GridPane.setConstraints(title, 0, 0, 6, 1);
		GridPane.setHalignment(title, HPos.CENTER);

		//1 column
		studentPane.add(fName, 0, 1);
		studentPane.add(lName, 0, 2);
		studentPane.add(course, 0, 3);
		
		//2 column
		studentPane.add(txtFname, 1, 1);
		studentPane.add(txtLname, 1, 2);
		studentPane.add(cbCourse, 1, 3);
		
		//3 column
		studentPane.add(id, 2, 1);
		studentPane.add(password, 2, 2);
		studentPane.add(email, 2, 3);

		//4 column
		studentPane.add(txtID, 3, 1);
		studentPane.add(txtPass, 3, 2);
		studentPane.add(txtEmail, 3, 3);
		
		//5 column
		studentPane.add(tel, 4, 1);
		studentPane.add(address, 4, 2);
		studentPane.add(postcode, 4, 3);
		
		//6 column
		studentPane.add(txtTel, 5, 1);
		studentPane.add(txtAddress, 5, 2);
		studentPane.add(txtPostcode, 5, 3);
		
		studentPane.add(lblStatus, 0, 4);

		GridPane.setConstraints(lblStatus, 0, 4, 6, 1);
		GridPane.setHalignment(lblStatus, HPos.CENTER);

		HBox boxControls = new HBox();
		
		//create control buttons
		Button btnAdd = new Button("Add");
		Button btnEdit = new Button("Edit");
		Button btnDelete = new Button("Delete record");
		Button btnClear = new Button("Clear");

		boxControls.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnClear);
		
		//set action for button add
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				studentTable.getSelectionModel().clearSelection();
				txtID.setText("none");
				Student student = new Student();
				int count = 0;
				
				String connectionType = "registration";
				lblStatus.setStyle("-fx-text-fill: red");
				
				for(TextField tf: txtArray) {
					if(!tf.getText().trim().isEmpty() && tf.getText() != null && count == 7 &&
							cbCourse.getValue() != null && txtLname.getText().trim().length() >= 3
							&& txtTel.getText().trim().length() >= 3) {
						student = saveStudent(connectionType);
						
						if(student == null) {
							lblStatus.setStyle("-fx-text-fill: red");
							lblStatus.setText("Student id is not unique.");
							break;
						}
						
						studentTable.getItems().add(student);
						lblStatus.setStyle("-fx-text-fill: green");
						lblStatus.setText("Student added.");
					} else if(count < 7 && !tf.getText().trim().isEmpty() && tf.getText() != null &&
							cbCourse.getValue() != null) {
						count++;
						lblStatus.setText("Enter all details.");
					} else {
						lblStatus.setText("Enter all details.");
					}
				}

			}
		});
		
		//set action for button edit
		btnEdit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String connectionType = "saveInfo";
				
				if(studentTable.getSelectionModel().getSelectedItem() != null) {
					Student s = saveStudent(connectionType);
					studentTable.getItems().remove(studentTable.getSelectionModel().getSelectedItem());
					studentTable.getItems().add(s);
					lblStatus.setText("Student information updated.");
					lblStatus.setStyle("-fx-text-fill: green;");
				} else {
					lblStatus.setText("Please select student from table.");
					lblStatus.setStyle("-fx-text-fill: red;");
				}
				
				studentTable.getSelectionModel().clearSelection();
			}
		});
		
		//set action fro button delete
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {
			
			Student student = new Student();

			@Override
			public void handle(ActionEvent event) {
				
				student = studentTable.getSelectionModel().getSelectedItem();
				
				String studentId = student.getId();
				
				String connectionType = "deleteStudent";
				
				Client client = new Client();
				
				String status = client.removeRecord(connectionType, studentId);
				
				lblStatus.setText(status);
				lblStatus.setStyle("-fx-text-fill: green;");
				
				studentTable.getItems().remove(student);
				
				for(TextField tf: txtArray) { tf.clear(); }
				cbCourse.setValue(null);
				
				studentTable.getSelectionModel().clearSelection();
				
			}
		});
		
		//set action for button clear
		btnClear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				for(TextField tf: txtArray) { tf.clear(); }
				cbCourse.setValue(null);
				studentTable.getSelectionModel().clearSelection();
			}
			
			
		});

		boxControls.setSpacing(30);
		boxControls.setAlignment(Pos.CENTER);
		boxControls.setPadding(new Insets(50, 0, 0, 0));

		studentPane.getChildren().add(boxControls);
		GridPane.setConstraints(boxControls, 0, 4, 6, 1);

		studentPane.setPadding(new Insets(50, 40, 50, 40));

		studentPane.setVgap(10);
		studentPane.setHgap(20);
		
		studentTable.setFixedCellSize(25);
		studentTable.prefHeightProperty().bind(
				Bindings.size(studentTable.getItems()).multiply(studentTable.getFixedCellSize()).add(51));

		Client client = new Client();

		String connectionType = "viewAllStudentInfo";

		// add rows to the table view
		studentTable.getItems().addAll(client.getStudentList(connectionType));
		// add columns to the table view
		studentTable.getColumns().addAll(StudentTableViewHelper.getFirstNameColumn(),
				StudentTableViewHelper.getLastNameColumn(), StudentTableViewHelper.getIdColumn(),
				StudentTableViewHelper.getPassColumn(), StudentTableViewHelper.getCourseColumn(),
				StudentTableViewHelper.getTelColumn(), StudentTableViewHelper.getEmailColumn(),
				StudentTableViewHelper.getAddressColumn(), StudentTableViewHelper.getPostColumn());

		studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//studentTable.setEditable(false);
		studentTable.setPrefWidth(1100);
		studentTable.setPlaceholder(new Label("No visible columns and/or date exist."));
		
		studentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//if(event.getClickCount() > 1) {
					if(studentTable.getSelectionModel().getSelectedItem() != null) {
						Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
						txtFname.setText(selectedStudent.getFname());
						txtLname.setText(selectedStudent.getLname());
						txtID.setText(selectedStudent.getId());
						txtPass.setText(selectedStudent.getPass());
						cbCourse.setValue(selectedStudent.getCourse());
						
						txtTel.setText(selectedStudent.getTel());
						txtEmail.setText(selectedStudent.getEmail());
						txtAddress.setText(selectedStudent.getAddress());
						txtPostcode.setText(selectedStudent.getPostcode());
						
						lblStatus.setText(null);
						
					}
 				//}
			}
		});
		
		sp.setContent(studentTable);

		sp.setPrefHeight(300);

		sp.setPrefWidth(studentTable.getPrefWidth() + 20);
		
		studentTable.getSelectionModel().clearSelection();
		
		lblStatus.setText(null);
		
		fPane.getChildren().addAll(studentPane, sp);
		fPane.setPrefWidth(1100);
		
		fPane.setAlignment(Pos.CENTER);
		
		studentBox.getChildren().addAll(fPane);
		
		return studentBox;
	}
	
	@SuppressWarnings("unchecked")
	private VBox manageModule() {
		courseBox = new VBox();
		coursePane = new GridPane();
		
		FlowPane fPane = new FlowPane();
		ScrollPane sp = new ScrollPane();
		sp.setPannable(true);

        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		
		moduleTable = new TableView<>();
		
		Label lblmn = new Label("Module Name");
		Label lblRoom = new Label("Room No");
		Label lblDay = new Label("Day");
		Label lblTime = new Label("Time");
		Label lblLec = new Label("Lecturer");
		Label lblCourse = new Label("Course");
		
		Label title = new Label("Manage Timetable");

		title.setStyle(
				"-fx-font-family: Montserrat;" +
				"-fx-font-size: 25;");
		title.setPadding(new Insets(0, 0, 10, 0));
		
		lblStatus = new Label();
		
		txtmn = new TextField();
		txtRoom = new TextField();
		txtDay = new TextField();
		txtTime = new TextField();
		txtLec = new TextField();
		
		cbCourse = new ComboBox<String>();
		
		// add text fields and combo box to the grid pane
		cbCourse.getItems().addAll(null, "Computer Science", "Creative Computing", "Cyber Security");
		
		ArrayList<TextField> txtArray = new ArrayList<>();
		txtArray.add(txtmn);
		txtArray.add(txtRoom);
		txtArray.add(txtDay);
		txtArray.add(txtTime);
		txtArray.add(txtLec);
		
		courseBox.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				lblStatus.setText(null);
				cbCourse.setDisable(false);
				moduleTable.getSelectionModel().clearSelection();
				for(TextField tf : txtArray) {
					tf.clear();
					cbCourse.setValue(null);
				}
			}
		});
		coursePane.getChildren().add(title);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setConstraints(title, 0, 0, 6, 1);
		
		coursePane.add(lblmn, 0, 1);
		coursePane.add(lblRoom, 0, 2);
		
		coursePane.add(txtmn, 1, 1);
		coursePane.add(txtRoom, 1, 2);
		
		coursePane.add(lblDay, 2, 1);
		coursePane.add(lblTime, 2, 2);
		
		coursePane.add(txtDay, 3, 1);
		coursePane.add(txtTime, 3, 2);
		
		coursePane.add(lblLec, 4, 1);
		coursePane.add(lblCourse, 4, 2);
		
		coursePane.add(txtLec, 5, 1);
		coursePane.add(cbCourse, 5, 2);
		
		coursePane.getChildren().add(lblStatus);
		
		GridPane.setConstraints(lblStatus, 0, 3, 6, 1);
		GridPane.setHalignment(lblStatus, HPos.CENTER);
		
		Button addModule = new Button("Add");
		Button updateModule = new Button("Update");
		Button deleteModule = new Button("Delete");
		Button clear = new Button("Clear");
		
		HBox btnBox = new HBox();
		btnBox.getChildren().addAll(addModule, updateModule, deleteModule, clear);
		
		addModule.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String connectionType = "addModule";
				
				int count = 0;
				
				for(TextField tf : txtArray) {
					if(!tf.getText().trim().isEmpty() && tf.getText() != null && cbCourse.getValue() != null && count == 4) {
						Timetable t = saveModule(connectionType);
						moduleTable.getItems().add(t);
						lblStatus.setText("Module added.");
						lblStatus.setStyle("-fx-text-fill: green;");
					} else if(count < 4 && !tf.getText().trim().isEmpty() && tf.getText() != null &&
							cbCourse.getValue() != null) {
						count++;
						lblStatus.setText("Enter all details.");
						lblStatus.setStyle("-fx-text-fill: red;");
					} else {
						lblStatus.setText("Enter all details.");
						lblStatus.setStyle("-fx-text-fill: red;");
					}
				}
			}
		});
		
		updateModule.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String connectionType = "updateModule";
				
				if(moduleTable.getSelectionModel().getSelectedItem() != null) {
					Timetable t = saveModule(connectionType);
					moduleTable.getItems().remove(moduleTable.getSelectionModel().getSelectedItem());
					moduleTable.getItems().add(t);
				} else {
					lblStatus.setText("Please select module from table.");
					lblStatus.setStyle("-fx-text-fill: red;");
				}
				
				moduleTable.getSelectionModel().clearSelection();
			}
		});
		
		deleteModule.setOnAction(new EventHandler<ActionEvent>() {
			
			Timetable timetable = new Timetable();
			
			@Override
			public void handle(ActionEvent event) {
				String connectionType = "deleteModule";
				timetable = moduleTable.getSelectionModel().getSelectedItem();
				
				String moduleNo = timetable.getModuleNo();
				
				Client client = new Client();
				String status = client.removeRecord(connectionType, moduleNo);
				
				lblStatus.setText(status);
				lblStatus.setStyle("-fx-text-fill: green;");
				
				moduleTable.getItems().remove(timetable);

				for(TextField tf: txtArray) { tf.clear(); }
				cbCourse.setValue(null);

				moduleTable.getSelectionModel().clearSelection();
			}
		});
		
		clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				for(TextField tf: txtArray) { tf.clear(); }
				cbCourse.setValue(null);
				moduleTable.getSelectionModel().clearSelection();
			}
		});
		
		btnBox.setAlignment(Pos.CENTER);
		coursePane.getChildren().add(btnBox);
		GridPane.setConstraints(btnBox, 0, 3, 6, 1);
		btnBox.setSpacing(30);
		btnBox.setPadding(new Insets(50, 0, 0, 0));
		
		coursePane.setPadding(new Insets(50, 40, 50, 40));

		coursePane.setVgap(10);
		coursePane.setHgap(20);
		
		moduleTable.setFixedCellSize(25);
		moduleTable.prefHeightProperty().bind(
				Bindings.size(moduleTable.getItems()).multiply(moduleTable.getFixedCellSize()).add(51));
		
		Client client = new Client();

		String connectionType = "viewTimetable";

		// add rows to the table view
		moduleTable.getItems().addAll(client.getTimetableList(connectionType, "all"));
		// add columns to the table view
		moduleTable.getColumns().addAll(TimeTableViewHelper.getModuleNoColumn(), TimeTableViewHelper.getCourseNameColumn(),
				TimeTableViewHelper.getModuleNameColumn(), TimeTableViewHelper.getRoomNoColumn(),
				TimeTableViewHelper.getDayColumn(), TimeTableViewHelper.getTimeColumn(), TimeTableViewHelper.getLecColumn());

		moduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//moduleTable.setEditable(false);
		moduleTable.setPrefWidth(1100);
		moduleTable.setPlaceholder(new Label("No visible columns and/or date exist."));

		moduleTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//if(event.getClickCount() > 1) {
					if(moduleTable.getSelectionModel().getSelectedItem() != null) {
						Timetable selectedModule = moduleTable.getSelectionModel().getSelectedItem();
						txtmn.setText(selectedModule.getModuleName());
						txtRoom.setText(selectedModule.getRoomNo());
						txtDay.setText(selectedModule.getDay());
						txtTime.setText(selectedModule.getTime());
						txtLec.setText(selectedModule.getLec());
						cbCourse.setValue(selectedModule.getCourseName());

						lblStatus.setText(null);
						
						cbCourse.setDisable(true);


					}
				//}
			}
		});
		
		sp.setContent(moduleTable);
		
		sp.setPrefHeight(350);
		
		sp.setPrefWidth(moduleTable.getPrefWidth() + 20);
		
		moduleTable.getSelectionModel().clearSelection();
		
		fPane.setAlignment(Pos.CENTER);
		fPane.getChildren().addAll(coursePane, sp);
		
		courseBox.getChildren().add(fPane);
		
		return courseBox;
	}
	
	//update or add student information depends on connection type
	private Student saveStudent(String connectionType) {
		
		Student student = new Student();
		String result = "";
		
		//get information from text fields
		String fName = txtFname.getText().trim();
		String lName = txtLname.getText().trim();
		String tel = txtTel.getText().trim();
		String email = txtEmail.getText().trim();
		String address = txtAddress.getText().trim();
		String postcode = txtPostcode.getText().trim();
		String id = txtID.getText().trim();
		String password = txtPass.getText().trim();
		String course = cbCourse.getValue();
		
		//create student object
		student = new Student(fName, lName, tel, email, address, postcode, id, password, course);

		Client client = new Client();
		
		//send student object to server using the method defined in client class
		result = client.sendInfoToServer(connectionType, student);
		
		if(result.equals("false")) {
			//runs if and only if student id was not unique
			return null;
		} else {
			if(connectionType.equals("saveInfo")) {
				//result will be a string - Updated successful
				//studentTable.getItems().remove(studentTable.getSelectionModel().getSelectedItem());
				return student;
			} else if(connectionType.equals("registration")) {
				//result will be a new generated student id
				student.setId(result);
			}
			return student;
		}
	}
	
	private Timetable saveModule(String connectionType) {
		
		Timetable timetable = new Timetable();
		String moduleNo = "";
		
		String courseName = cbCourse.getValue();
		String moduleName = txtmn.getText();
		String room = txtRoom.getText();
		String day = txtDay.getText();
		String time = txtTime.getText();
		String lec = txtLec.getText();
		
		if(connectionType.equals("updateModule")) {
			moduleNo = moduleTable.getSelectionModel().getSelectedItem().getModuleNo();
		}
		
		timetable = new Timetable(moduleNo, courseName, moduleName, room, day, time, lec);
		
		Client client = new Client();
		
		//send info to server using method declared in client class
		String result = client.sendInfoToServer(connectionType, timetable);
		
		if(result.equals("false")) {
			lblStatus.setText("Error.");
		} else if(result.equals("true")) {
			lblStatus.setText("Module information updated successfully.");
			lblStatus.setStyle("-fx-text-fill: green;");
		} else {
			timetable.setModuleNo(result);
		}
		
		return timetable;
	}
}
