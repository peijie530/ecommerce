package ecommerce.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ecommerce.entity.User;
import ecommerce.entity.UserStatus;

public class SecurityUser implements UserDetails {
	
	// SecurityUser 繼承了 UserDetails，而 UserDetails 繼承了 Serializable，所以這裡要加上 serialVersionUID，避免序列化時出現警告
	private static final long serialVersionUID = 1L;
	
	private final User user;

	public SecurityUser(User user) {
		this.user = user;
	}
	
	// 回傳授權資訊清單（權限/角色）。Spring Security 會用它來做 @PreAuthorize(...) 的比對
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Enum 已經是 ROLE_ADMIN / ROLE_USER，因此直接回傳就能和 hasRole 對上
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getEmail(); // 用 email 當作 username
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true; // 帳號永不過期
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true; // 帳號永不鎖定
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 密碼永不過期
	}
	
	@Override
	public boolean isEnabled() {
		// 如果狀態是 BANNED 就不給過
        return user.getStatus() != UserStatus.BANNED;
	}
	
	
	
}
