package ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

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
        
        // CORS：跨來源資源共享（Cross-Origin Resource Sharing）
        .cors(cors -> cors.configurationSource(request -> {
        	CorsConfiguration config = new CorsConfiguration();
        	// 准許的來源（前端位址，開發環境通常是 http://
            config.setAllowedOrigins(java.util.List.of("http://127.0.0.1:5500")); 
            // 准許的 HTTP 方法
            config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            // 准許的 HTTP 標頭(Header)，JWT 通常放在Header裡面
            config.setAllowedHeaders(java.util.List.of("*"));
            // 是否允許攜帶 JWT 等認證資訊（Cookie、Authorization Header）
            config.setAllowCredentials(true);
            return config;
        }))
        
        	// H2 Console 需要的設定（開發環境專用）
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            
            
            // 設定誰可以進哪些門
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // H2 資料庫：免證件
                .requestMatchers("/api/v1/products/**").permitAll() // 商品列表：免證件（讓客人看貨）
                .requestMatchers("/api/v1/auth/**").permitAll()     // 註冊登入：免證件
                .requestMatchers("/api/v1/users/**").authenticated() // 用戶管理：必須帶 Token 登入（但不限制角色，讓 admin 和 user 都能進）
                .anyRequest().authenticated() // 其他（購物車、訂單）：必須帶 Token 登入
            )
            
         
        // 加入 JWT 過濾器（門衛），讓它在 UsernamePasswordAuthenticationFilter 之前工作，攔截請求並驗證 JWT
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 加入 JWT 過濾器（門衛）
        return http.build();
    }
}