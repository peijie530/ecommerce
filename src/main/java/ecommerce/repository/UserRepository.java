package ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);  // 用 email 找到使用者資料（登入驗證、重設密碼、寄信）
	boolean existsByEmail(String email);   // 註冊時要檢查是否重複
	
}
