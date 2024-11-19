package com.hexaware.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsersDTO {
	
	private int userId;
	
	@NotBlank(message="UserName cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]{5,30}$", message = "Can contain alphabets,spaces, length should be between 5 and 30")
	private String userName;
	
	@NotNull(message = "Password should not be null")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one number, and one special character")
	private String password;
	public UsersDTO() {
		super();
	}
	public UsersDTO(int userId, String userName, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return String.format("Users [userId=%s, userName=%s, password=%s]", userId, userName, password);
	}
	
}
