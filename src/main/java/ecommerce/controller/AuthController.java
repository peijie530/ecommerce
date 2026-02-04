package ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.dto.AuthResponse;
import ecommerce.dto.LoginRequest;
import ecommerce.dto.RegisterRequest;
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
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
		userService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		String token = userService.login(request);
		
		// 將字串包裝成 JSON 物件回傳
		return ResponseEntity.ok(new AuthResponse(token));
	}
	
}
