package ecommerce.service;

import ecommerce.dto.LoginRequest;
import ecommerce.dto.RegisterRequest;
import ecommerce.entity.User;

public interface UserService {

	User register(RegisterRequest request);
	String login(LoginRequest request);
}
