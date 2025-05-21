package com.drive.EvaluationProject.DTO;

import com.drive.EvaluationProject.modal.UserRole;

public class UserRegistrationDto {
    private String username;
    private String password;
    private UserRole role;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserRegistrationDto [username=" + username + ", password=" + password + ", role=" + role + "]";
	}
	
}
