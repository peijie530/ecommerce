package ecommerce.dto;

import ecommerce.entity.UserRole;
import ecommerce.entity.UserStatus;

public class UserResponse {

	private Long id;
	private String email;
	private String name;
	private UserRole role;
	private UserStatus status;
	
	public UserResponse(Long id, String email, String name, UserRole role, UserStatus status) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.role = role;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return name;
	}
	
	public UserRole getRole() {
		return role;
	}
	
	public UserStatus getStatus() {
		return status;
	}
	
}
