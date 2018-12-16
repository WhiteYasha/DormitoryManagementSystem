package helloworld;

import com.jfoenix.controls.*;

public class Admin {
	private String name, password;
	private JFXButton button;
	
	public Admin(String n, String p) {
		this.name = n;
		this.password = p;
	}
	
	public void setName(String name) {this.name = name;}
	public void setPassword(String password) {this.password = password;}
	public void setButton(JFXButton button) {this.button = button;}
	
	public String getName() {return this.name;}
	public String getPassword() {return this.password;}
	public JFXButton getButton() {return this.button;}
}
