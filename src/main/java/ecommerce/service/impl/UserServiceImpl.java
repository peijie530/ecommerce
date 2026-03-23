package ecommerce.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ecommerce.config.JwtUtils;
import ecommerce.config.SecurityUser;
import ecommerce.dto.LoginRequest;
import ecommerce.dto.RegisterRequest;
import ecommerce.dto.UserResponse;
import ecommerce.entity.User;
import ecommerce.entity.UserRole;
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
	@Transactional  
	public UserResponse register(RegisterRequest request) {
		
		// 檢查 Email
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該電子郵件已被註冊");
		}
		
		// 加密密碼
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		// 建立並儲存
		User user = new User(request.getEmail(), encodedPassword, request.getName());
		
		return toResponse(userRepository.save(user)); // 儲存後會有 ID 等資訊，轉成 UserResponse 回傳
		
	}
	
	// 實作 UserDetailsService 的核心方法
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// 1. 去資料庫撈出原始的 User Entity
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("找不到該用戶: " + email));
		return new SecurityUser(user); // 把 User 包成 SecurityUser 回傳，讓 Spring Security 知道怎麼驗證
	}
	
	
	@Override
	public String login(LoginRequest request) {
		
		// 根據 Email 查找用戶
	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "帳號或密碼錯誤"));

	    // 比對密碼 (使用 passwordEncoder.matches)
	    // 注意：不能用 equals()，因為資料庫存的是 BCrypt 加密後的字串
	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "帳號或密碼錯誤");
	    }

	    // 檢查帳號狀態
	    if (user.getStatus() == UserStatus.BANNED) {
	        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "帳號已被停權");
	    }

	    // 生成並回傳 JWT
	    return jwtUtils.generateToken(user.getEmail());
	}
	
	@Override
	public List<UserResponse> findAll() {
        return userRepository.findAll()
        		.stream()
				.map(this::toResponse)  // 將清單中每個 User 轉成 UserResponse
				.toList();
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該用戶"));
		return toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該用戶"));
        user.setStatus(status);
        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateRole(Long id, UserRole role) {
        User user = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該用戶"));
        user.setRole(role);
		return toResponse(userRepository.save(user));
    }
    
    @Override
    @Transactional
    public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該用戶"));
		userRepository.delete(user);
	}
    
    // 核心工具方法：負責轉型，保護密碼不外流
    private UserResponse toResponse(User user) {
		return new UserResponse(
				user.getId(),
				user.getEmail(),
				user.getName(),
				user.getRole(),
				user.getStatus()
		);
	}
}