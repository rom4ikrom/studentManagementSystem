package myApp.domain;

import java.io.Serializable;

public class Student implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fName;
	private String lName;
	private String tel;
	private String email;
	private String address;
	private String postcode;
	private String id;
	private String pass;
	private String course;
	
	public Student() {
		this.fName = "";
		this.lName = "";
		this.tel = "";
		this.email = "";
		this.address = "";
		this.postcode = "";
		this.id = "none";
		this.pass = "none";
		this.course = "";
	}
	
	public Student(String fn, String ln, String tel, String e, String a, String ps, String id, String pass, String c) {
		this.fName = fn;
		this.lName = ln;
		this.tel =tel;
		this.email = e;
		this.address = a;
		this.postcode = ps;
		this.id = id;
		this.pass = pass;
		this.course = c;
	}
	
	public String getFname() {
		return fName;
	}
	
	public void setFname(String fn) {
		this.fName = fn;
	}
	
	public String getLname() {
		return lName;
	}
	
	public void setLname(String ln) {
		this.lName = ln;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String t) {
		this.tel = t;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String e) {
		this.email = e;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String a) {
		this.address = a;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String p) {
		this.postcode = p;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String i) {
		this.id = i;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPassword(String pass) {
		this.pass = pass;
	}
	
	public String getCourse() {
		return course;
	}
	
	public void setCourse(String c) {
		this.course = c;
	}

}
