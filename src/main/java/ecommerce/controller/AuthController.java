package ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.dto.AuthResponse;
import ecommerce.dto.LoginRequest;
import ecommerce.dto.RegisterRequest;
import ecommerce.dto.UserResponse;
import ecommerce.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final UserService userService;
	
	// 構造函數注入Service
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("/register")
	public UserResponse register(@Valid @RequestBody RegisterRequest request) {
		return userService.register(request);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		String token = userService.login(request);
		
		return new AuthResponse(token);
		
	}
	
}
