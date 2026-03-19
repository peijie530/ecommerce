package ecommerce.service;

import ecommerce.dto.LoginRequest;
import ecommerce.dto.RegisterRequest;
import ecommerce.entity.User;
import ecommerce.entity.UserRole;
import ecommerce.entity.UserStatus;
import java.util.List;

public interface UserService {

	User register(RegisterRequest request);
	String login(LoginRequest request);
    List<User> findAll();
    User findById(Long id);
    User updateStatus(Long id, UserStatus status);
    User updateRole(Long id, UserRole role);
}