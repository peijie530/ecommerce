package ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	// BCryptPasswordEncoder：負責把密碼變成 hash（不可逆）強大的單向加密演算法 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();    
    }

    
    // SecurityFilterChain：用來定義「Security 的過濾規則」（誰能進哪些 API）
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        // 關閉 CSRF 並允許所有請求，方便我們測試註冊 API
        http.csrf(csrf -> csrf.disable())
        
        	// 2. 允許 H2 Console 使用 iframe 顯示網頁內容
        	.headers(headers -> headers.frameOptions(frame -> frame.disable()))
        	// 3. 設定權限
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/h2-console/**").permitAll() // 確保 H2 路徑完全放行
            		.anyRequest().permitAll() // 其他請求也都先放行
            ); 
        return http.build();
    }
}