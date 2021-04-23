package com.java.example.demo.entity;




import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Users{
	
	public Users(int iD, String userName, String password, String role) {
		super();
		ID = iD;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}
	private int ID;
	private String userName;
	private String password;
	private String role;
	
}
