package myApp.domain;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

//it supports the following features
//creation of the data rows
//creation of the columns for the table
public class StudentTableViewHelper {

	//return student's first name column
	public static TableColumn<Student, String> getFirstNameColumn() {
		TableColumn<Student, String> fnCol = new TableColumn<>("First Name");
		PropertyValueFactory<Student, String> fnCellValueFactory = new PropertyValueFactory<>("Fname");
		fnCol.setCellValueFactory(fnCellValueFactory);
		return fnCol;
	}

	//return student's last name column
	public static TableColumn<Student, String> getLastNameColumn() {
		TableColumn<Student, String> lnCol = new TableColumn<>("Last Name");
		PropertyValueFactory<Student, String> lnCellValueFactory = new PropertyValueFactory<>("Lname");
		lnCol.setCellValueFactory(lnCellValueFactory);
		return lnCol;
	}
	
	//return student's id column
	public static TableColumn<Student, String> getIdColumn() {
		TableColumn<Student, String> idCol = new TableColumn<>("Student ID");
		PropertyValueFactory<Student, String> idCellValueFactory = new PropertyValueFactory<>("id");
		idCol.setCellValueFactory(idCellValueFactory);
		return idCol;
	}

	//return student's telephone column
	public static TableColumn<Student, String> getTelColumn() {
		TableColumn<Student, String> telCol = new TableColumn<>("Telephone");
		PropertyValueFactory<Student, String> telCellValueFactory = new PropertyValueFactory<>("tel");
		telCol.setCellValueFactory(telCellValueFactory);
		return telCol;
	}
	//return student's email column
	public static TableColumn<Student, String> getEmailColumn() {
		TableColumn<Student, String> emailCol = new TableColumn<>("Email");
		PropertyValueFactory<Student, String> emailCellValueFactory = new PropertyValueFactory<>("email");
		emailCol.setCellValueFactory(emailCellValueFactory);
		return emailCol;
	}
	//return student's address column
	public static TableColumn<Student, String> getAddressColumn() {
		TableColumn<Student, String> addressCol = new TableColumn<>("Address");
		PropertyValueFactory<Student, String> addressCellValueFactory = new PropertyValueFactory<>("address");
		addressCol.setCellValueFactory(addressCellValueFactory);
		return addressCol;
	}
	//return student's postcode column
	public static TableColumn<Student, String> getPostColumn() {
		TableColumn<Student, String> postCol = new TableColumn<>("Postcode");
		PropertyValueFactory<Student, String> postCellValueFactory = new PropertyValueFactory<>("postcode");
		postCol.setCellValueFactory(postCellValueFactory);
		return postCol;
	}
	
	//return student's password column
	public static TableColumn<Student, String> getPassColumn() {
		TableColumn<Student, String> passCol = new TableColumn<>("Password");
		PropertyValueFactory<Student, String> passCellValueFactory = new PropertyValueFactory<>("pass");
		passCol.setCellValueFactory(passCellValueFactory);
		return passCol;
	}

	//return student's course column
	public static TableColumn<Student, String> getCourseColumn() {
		TableColumn<Student, String> courseCol = new TableColumn<>("Course");
		PropertyValueFactory<Student, String> courseCellValueFactory = new PropertyValueFactory<>("course");
		courseCol.setCellValueFactory(courseCellValueFactory);
		return courseCol;
	}
}
