package ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	// 注入 JWT 過濾器 (門衛)
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

    
    // SecurityFilterChain：用來定義「Security 的過濾規則」（誰能進哪些 API）
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	// 關閉不需要的防護（開發環境 H2 必備）
        http.csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            
            // 設定誰可以進哪些門
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // H2 資料庫：免證件
                .requestMatchers("/api/v1/products/**").permitAll() // 商品列表：免證件（讓客人看貨）
                .requestMatchers("/api/v1/auth/**").permitAll()     // 註冊登入：免證件
                .requestMatchers("/api/v1/users/**").authenticated() // 用戶管理：必須帶 Token 登入（但不限制角色，讓 admin 和 user 都能進）
                .anyRequest().authenticated() // 其他（購物車、訂單）：必須帶 Token 登入
            )

        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 加入 JWT 過濾器（門衛）
        return http.build();
    }
}