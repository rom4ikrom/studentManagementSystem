package myApp.domain;

import java.io.Serializable;

public class LoginInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String pass;
	
	public LoginInfo(String u, String p) {
		this.username = u;
		this.pass = p;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String p) {
		this.pass = p;
	}
}
