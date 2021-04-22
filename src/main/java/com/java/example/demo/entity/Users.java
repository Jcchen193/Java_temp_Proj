package com.java.example.demo.entity;




import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Users{
	
	private int ID;
	private String userName;
	private String password;
	private String role;
	
}
