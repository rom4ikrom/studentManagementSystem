package myApp.ui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import myApp.domain.Client;
import myApp.domain.Student;
import myApp.domain.Timetable;
import myApp.ui.ClientLoginUI;
import myApp.domain.TimeTableViewHelper;

public class ClientAccManUI {
	
	private Label lblWelcome = new Label("Welcome back, student!");
	
	private ImageView imageView;
	
	private Button btnUpdateInfo = new Button("Update Info");
	private Button btnSaveInfo = new Button("Save Info");
	private Button btnViewInfo = new Button("View Info");
	
	private Label lblFullName = new Label("Full Name");
	private Label lblEmail = new Label("Email");
	private Label lblTel = new Label("Telephone");
	
	private Label lblCourse = new Label("Course");
	private Label lblAddress = new Label("Address");
	private Label lblPostcode = new Label("Postcode");
	
	private TextField txtFullName = new TextField();
	private TextField txtEmail = new TextField();
	private TextField txtTel = new TextField();
	
	private ComboBox<String> cbCourse = new ComboBox<String>();
	private TextField txtAddress = new TextField();
	private TextField txtPostcode = new TextField();
	
	private Label lblStatus = new Label();
	
	private ArrayList<TextField> txtArray = new ArrayList<TextField>();
	
	private FlowPane timeTablePane;
	private TableView<Timetable> timeTable;
	
	private Stage stage;
	private VBox root;
	private GridPane gPane;
	
	public void studentAccount() {
		
		//create new stage
		stage = new Stage();
		
		//create root pane  - vertical box
		root = new VBox();
		
		//create menu
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(stage.widthProperty());
		
		//account menu - student can view courses, view timetable, log out and exit
	    Menu accMenu = new Menu("Account");
	    MenuItem viewTimetableAccMenu = new MenuItem("View Timetable");
	    MenuItem logOutAccMenu = new MenuItem("Log Out");
	    MenuItem exitAccMenu = new MenuItem("Exit");
	    
	    //add action to view timetable option in account menu
	    viewTimetableAccMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(cbCourse.getValue() == null) {
					lblStatus.setText("Choose course first and save!");
					lblStatus.setStyle(
							"-fx-text-fill: red");
				} else {
					showTimetable();
				}
				
			}
	    	
	    });
	    
	    //add action to log out option in account menu
	    logOutAccMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ClientLoginUI clientLogin = new ClientLoginUI();
				clientLogin.studentLogin();
				stage.close();
			}
	    	
	    });
	    
	    //add action to exit option in account menu
	    exitAccMenu.setOnAction(actionEvent -> Platform.exit());
	    
	    //add all options to menu
	    accMenu.getItems().addAll(viewTimetableAccMenu,
	        new SeparatorMenuItem(), logOutAccMenu, exitAccMenu);
	    
	    //add account menu to menu bar
	    menuBar.getMenus().add(accMenu);
	    
	    //add menu bar to pane
		root.getChildren().add(menuBar);
		
		//add welcome label to the scene
		root.getChildren().add(lblWelcome);
		
		lblWelcome.setStyle(
				"-fx-font-family: Montserrat;" +
				"-fx-font-size: 25;");
		
		//create a new grid layout for general information
		gPane = new GridPane();
		
		//image and image view (icon)
		Image accIcon = new Image("resources/accIcon.png");
		imageView = new ImageView(accIcon);
		
		gPane.getChildren().add(imageView);
		
		GridPane.setConstraints(imageView, 0, 0, 1, 5);
		
		//add labels to the grid pane
		gPane.getChildren().addAll(lblFullName, lblEmail, lblTel);
		
		GridPane.setConstraints(lblFullName, 1, 0);
		GridPane.setConstraints(lblEmail, 1, 1);
		GridPane.setConstraints(lblTel, 1, 2);
		
		//add text fields to the grid pane
		gPane.getChildren().addAll(txtFullName, txtEmail, txtTel);
		
		GridPane.setConstraints(txtFullName, 2, 0);
		GridPane.setConstraints(txtEmail, 2, 1);
		GridPane.setConstraints(txtTel, 2, 2);
		
		//add labels to the grid pane
		gPane.getChildren().addAll(lblCourse, lblAddress, lblPostcode);
		
		GridPane.setConstraints(lblCourse, 3, 0);
		GridPane.setConstraints(lblAddress, 3, 1);
		GridPane.setConstraints(lblPostcode, 3, 2);
		
		//add text fields and combo box to the grid pane
		cbCourse.getItems().addAll(
				"Computer Science",
				"Creative Computing",
				"Cyber Security");
		
		//add text fields to the grid pane
		gPane.getChildren().addAll(cbCourse, txtAddress, txtPostcode);
		
		GridPane.setConstraints(cbCourse, 4, 0);
		GridPane.setConstraints(txtAddress, 4, 1);
		GridPane.setConstraints(txtPostcode, 4, 2);
		
		gPane.getChildren().add(lblStatus);
		lblStatus.setStyle("-fx-font-size: 12;");
		GridPane.setConstraints(lblStatus, 1, 4, 4, 1);
		
		
		//create horizontal box for a control buttons
		HBox btnBox = new HBox();
		
		//add buttons to the box
		btnBox.getChildren().addAll(btnViewInfo, btnUpdateInfo, btnSaveInfo);
		
		btnViewInfo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getStudentInfo();
				
			}
		});
		
		//make all text fields editable for student to edit the information
		btnUpdateInfo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for(TextField t : txtArray) {
					t.setEditable(true);
				}
				
				cbCourse.setDisable(false);
				lblStatus.setText(null);
			}
			
		});
		
		btnSaveInfo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveStudentInfo();
				for(TextField t : txtArray) {
					t.setEditable(false);
				}
				cbCourse.setDisable(true);
			}
		});
		
		btnBox.setSpacing(30);
		
		//add box to the pane
		gPane.getChildren().add(btnBox);
		btnBox.setAlignment(Pos.CENTER);
		GridPane.setConstraints(btnBox, 1, 5, 4, 1);
		
		//set paddings for the pane
		gPane.setPadding(new Insets (40, 40, 40, 40));
		
		//The height of the vertical gaps between rows.
		gPane.setVgap(10);
		//The width of the horizontal gaps between columns.
		gPane.setHgap(10);
		
		gPane.setVgap(20);
		gPane.setHgap(20);
		
		//set sizes of image
		imageView.setFitWidth(150);
		imageView.setFitHeight(150);
		
		FlowPane fPane = new FlowPane();
		fPane.getChildren().add(gPane);
		
		fPane.setAlignment(Pos.CENTER);
		
		root.getChildren().add(fPane);
		
		//add pane to scene
		Scene scene = new Scene(root, 1000, 700);
		
		stage.setScene(scene);
		stage.setTitle("Student Account");
		stage.show();
		
		//set the same width to all buttons
		double width= btnUpdateInfo.getWidth();

		btnUpdateInfo.setPrefWidth(width);
		btnSaveInfo.setPrefWidth(width);
		btnViewInfo.setPrefWidth(width);
		
		//add all text field to one array
		txtArray.add(txtFullName);
		txtArray.add(txtEmail);
		txtArray.add(txtTel);
		txtArray.add(txtAddress);
		txtArray.add(txtPostcode);
		
		//set minimum width for test fields and set editable to false
		for(TextField t : txtArray) {
			t.setMinWidth(200);
			t.setEditable(false);
		}

		cbCourse.setMinWidth(200);
		cbCourse.setDisable(true);
		
		getStudentInfo();
		
		lblWelcome.setPadding(new Insets(0, 0, 0, ((stage.getWidth() - lblWelcome.getWidth()) / 2)));
	}
	
	private void getStudentInfo() {
		String connectionType = "viewInfo";
		
		Client client = new Client();
		
		Student student = client.getInfoFromServer(connectionType);
		
		String fullName = "";
		String empty = "none";
		
		if(student.getFname() != null && student.getLname() != null) {
			fullName = student.getFname() + " " + student.getLname();
			txtFullName.setText(fullName);
		} else {
			txtFullName.setText(empty);
		}
		
		txtEmail.setText(student.getEmail());
		txtTel.setText(student.getTel());
		txtAddress.setText(student.getAddress());
		txtPostcode.setText(student.getPostcode());

		cbCourse.setValue(student.getCourse());
		
		cbCourse.setDisable(true);
		
		lblStatus.setText("");
	}
	
	private void saveStudentInfo() {
		
		String connectionType = "saveInfo";
		
		int count = 0;
		
		for(TextField tf : txtArray) {
			if(!tf.getText().trim().isEmpty() && tf.getText() != null && count == 4 && cbCourse.getValue() != null) {
				Client client = new Client();

				String fullName = txtFullName.getText().trim();

				String fName = "";
				String lName = "";

				//get first name and last name from the full name in order to save it correctly to database
				if(fullName.contains(" ")) {
					fName = fullName.substring(0, fullName.indexOf(" "));
					lName = fullName.substring(fullName.indexOf(" ") + 1, fullName.length());
				} else {
					fName = fullName;
				}

				String email = txtEmail.getText().trim();
				String tel = txtTel.getText().trim();
				String course = cbCourse.getValue();
				String address = txtAddress.getText().trim();
				String postcode = txtPostcode.getText().trim();

				Student student = new Student(fName, lName, tel, email, address, postcode, "id", "pass", course);

				String savedInfo = client.sendInfoToServer(connectionType, student);
				lblStatus.setText(savedInfo);
				lblStatus.setStyle(
						"-fx-text-fill: green");
			} else if(count < 4 && !tf.getText().trim().isEmpty() && tf.getText() != null && cbCourse.getValue() != null) {
				count++;
				lblStatus.setText("Enter all details.");
				lblStatus.setStyle(
						"-fx-text-fill: red");
			} else {
				lblStatus.setText("Enter all details.");
				lblStatus.setStyle(
						"-fx-text-fill: red");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void showTimetable() {
		
		ScrollPane sp = null;

		if(root.getChildren().indexOf(timeTablePane) == -1) {
			timeTablePane = new FlowPane();
			timeTable = new TableView<Timetable>();
			
			sp = new ScrollPane();
			sp.setPannable(true);

	        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
			
			String connectionType = "viewTimetable";
			String courseName = cbCourse.getValue();
			Client client = new Client();
			
			timeTable.setPrefWidth(gPane.getWidth());
			
			//get info from database
			
			//add rows to the table view
			timeTable.getItems().addAll(client.getTimetableList(connectionType, courseName));
			//add columns to the table view
			timeTable.getColumns().addAll(TimeTableViewHelper.getModuleNameColumn(), TimeTableViewHelper.getRoomNoColumn(),
					TimeTableViewHelper.getDayColumn(), TimeTableViewHelper.getTimeColumn(), TimeTableViewHelper.getLecColumn());
			
			timeTable.setFixedCellSize(25);
			timeTable.prefHeightProperty().bind(
					Bindings.size(timeTable.getItems()).multiply(timeTable.getFixedCellSize()).add(51));
			
			timeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			timeTable.setPlaceholder(new Label("No visible columns and/or date exist."));
			
			
			
			timeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			timeTable.setPlaceholder(new Label("No visible columns and/or date exist."));
			
			sp.setContent(timeTable);
			
			timeTablePane.getChildren().add(sp);
			timeTablePane.setAlignment(Pos.CENTER);
			
			root.getChildren().add(timeTablePane);
			
		} else {
			timeTablePane.getChildren().remove(timeTablePane);
			root.getChildren().remove(timeTablePane);
		}
		
	}
}
