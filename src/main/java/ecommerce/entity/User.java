package ecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)  // 自動記錄時間
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;  // 儲存加密後的 hash
	
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)  // 把 enum 存進資料庫時，用字串存
	@Column(nullable = false)
	private UserRole role = UserRole.ROLE_USER;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserStatus status = UserStatus.ACTIVE;
	
	@CreatedDate  // 第一次存進 DB（新增）時自動填入時間
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate  // 每次更新時自動更新「最後修改時間」
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	protected User() {} // JPA 從 DB 撈資料時，需要先「new 一個空物件」再把欄位塞進去，所以必須要有無參數建構子
	
	public User(String email, String password, String name) {  // 在註冊或建立使用者時用
		this.email = email;
		this.password = password;
		this.name = name;
	}
	
	
	// getters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
}
