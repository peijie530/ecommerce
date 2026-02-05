package ecommerce.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ecommerce.config.JwtUtils;
import ecommerce.dto.LoginRequest;
import ecommerce.dto.RegisterRequest;
import ecommerce.entity.User;
import ecommerce.entity.UserStatus;
import ecommerce.repository.UserRepository;
import ecommerce.service.UserService;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
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
	
	@Override
	public String login(LoginRequest request) {
	    // 1. 根據 Email 找用戶
	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "帳號或密碼錯誤"));

	    // 2. 比對密碼 (使用 passwordEncoder.matches)
	    // 注意：不能用 equals()，因為資料庫存的是 BCrypt 加密後的字串
	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "帳號或密碼錯誤");
	    }

	    // 3. 檢查帳號狀態
	    if (user.getStatus() == UserStatus.BANNED) {
	        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "帳號已被停權");
	    }

	    // 4. 生成並回傳 JWT
	    return jwtUtils.generateToken(user.getEmail());
	}
}
