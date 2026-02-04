package ecommerce.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ecommerce.dto.RegisterRequest;
import ecommerce.entity.User;
import ecommerce.repository.UserRepository;
import ecommerce.service.UserService;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	@Transactional  // 確保資料庫操作的原子性
	public User register(RegisterRequest request) {
		
		// 檢查 Email
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該電子郵件已被註冊");
		}
		
		// 加密密碼
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		// 建立並儲存
		User user = new User(request.getEmail(), encodedPassword, request.getName());
		
		return userRepository.save(user);
		
	}
}
