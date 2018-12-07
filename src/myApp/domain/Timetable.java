package myApp.domain;

import java.io.Serializable;

public class Timetable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String moduleNo;
	private String courseName;
	private String moduleName;
	private String roomNo;
	private String day;
	private String cTime;
	private String lecturer;
	
	public Timetable() {
		this.moduleNo = "";
		this.courseName = "";
		this.moduleName = "";
		this.roomNo = "";
		this.day = "";
		this.cTime = "";
		this.lecturer = "";
	}
	
	public Timetable(String mNo, String cn, String mn, String r, String d, String t, String l) {
		this.moduleNo = mNo;
		this.courseName = cn;
		this.moduleName = mn;
		this.roomNo = r;
		this.day = d;
		this.cTime = t;
		this.lecturer = l;
	}
	
	public String getModuleNo() {
		return moduleNo;
	}
	
	public void setModuleNo(String mNo) {
		this.moduleNo = mNo;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String cn) {
		this.courseName = cn;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public void setModuleName(String mn) {
		this.moduleName = mn;
	}
	
	public String getRoomNo() {
		return roomNo;
	}
	
	public void setRoomNo(String r) {
		this.roomNo = r;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String d) {
		this.day = d;
	}
	
	public String getTime() {
		return cTime;
	}
	
	public void setTime(String t) {
		this.cTime = t;
	}
	
	public String getLec() {
		return lecturer;
	}
	
	public void setLec(String l) {
		this.lecturer = l;
	}
}
