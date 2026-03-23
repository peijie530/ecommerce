package ecommerce.service;

import ecommerce.dto.LoginRequest;
import ecommerce.dto.RegisterRequest;
import ecommerce.dto.UserResponse;
import ecommerce.entity.UserRole;
import ecommerce.entity.UserStatus;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

	UserResponse register(RegisterRequest request);
	String login(LoginRequest request);
    List <UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse updateStatus(Long id, UserStatus status);
    UserResponse updateRole(Long id, UserRole role);
    void deleteUser(Long id);
}