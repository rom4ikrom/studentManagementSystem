package myApp.domain;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;;

//it supports the following features
//creation of the data rows
//creation of the columns for the table
public class TimeTableViewHelper {
	
	//return timetable module no column
	public static TableColumn<Timetable, String> getModuleNoColumn() {
		TableColumn<Timetable, String> mNoCol = new TableColumn<>("Module No");
		PropertyValueFactory<Timetable, String> mNoCellValueFactory = new PropertyValueFactory<>("moduleNo");
		mNoCol.setCellValueFactory(mNoCellValueFactory);
		return mNoCol;
	}
	
	//return timetable course name column
	public static TableColumn<Timetable, String> getCourseNameColumn() {
		TableColumn<Timetable, String> cnCol = new TableColumn<>("Course Name");
		PropertyValueFactory<Timetable, String> cnCellValueFactory = new PropertyValueFactory<>("courseName");
		cnCol.setCellValueFactory(cnCellValueFactory);
		return cnCol;
	}
	
	//return timetable module name column
	public static TableColumn<Timetable, String> getModuleNameColumn() {
		TableColumn<Timetable, String> mnCol = new TableColumn<>("Module Name");
		PropertyValueFactory<Timetable, String> mnCellValueFactory = new PropertyValueFactory<>("moduleName");
		mnCol.setCellValueFactory(mnCellValueFactory);
		return mnCol;
	}

	//return timetable room number column
	public static TableColumn<Timetable, String> getRoomNoColumn() {
		TableColumn<Timetable, String> roomCol = new TableColumn<>("Room Number");
		PropertyValueFactory<Timetable, String> roomCellValueFactory = new PropertyValueFactory<>("roomNo");
		roomCol.setCellValueFactory(roomCellValueFactory);
		return roomCol;
	}

	//return timetable day column
	public static TableColumn<Timetable, String> getDayColumn() {
		TableColumn<Timetable, String> dayCol = new TableColumn<>("Day");
		PropertyValueFactory<Timetable, String> dayCellValueFactory = new PropertyValueFactory<>("day");
		dayCol.setCellValueFactory(dayCellValueFactory);
		return dayCol;
	}

	//return timetable time column
	public static TableColumn<Timetable, String> getTimeColumn() {
		TableColumn<Timetable, String> timeCol = new TableColumn<>("Time");
		PropertyValueFactory<Timetable, String> timeCellValueFactory = new PropertyValueFactory<>("time");
		timeCol.setCellValueFactory(timeCellValueFactory);
		return timeCol;
	}

	//return timetable lecturer column
	public static TableColumn<Timetable, String> getLecColumn() {
		TableColumn<Timetable, String> lecCol = new TableColumn<>("Lecturer");
		PropertyValueFactory<Timetable, String> lecCellValueFactory = new PropertyValueFactory<>("lec");
		lecCol.setCellValueFactory(lecCellValueFactory);
		return lecCol;
	}
	
}
