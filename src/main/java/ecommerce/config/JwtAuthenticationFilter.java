package ecommerce.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ecommerce.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtUtils jwtUtils;
	private final UserService userService;
	
	public JwtAuthenticationFilter(JwtUtils jwtUtils, UserService userService) {
		this.jwtUtils = jwtUtils;
		this.userService = userService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 從請求 Header 中取得 Authorization 資訊
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		
		// 檢查 Header 格式是否正確 (必須是 Bearer 開頭)
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// 提取 Token 字串 (去掉 "Bearer " 這 7 個字元)
		jwt = authHeader.substring(7);
		
		// 從 Token 中解析出使用者 Email
		userEmail = jwtUtils.getEmailFromToken(jwt);
		
		// 如果有 Email，且目前 Spring Security 系統裡還沒有認證資訊
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			// 呼叫在 UserServiceImpl 實作的 loadUserByUsername 方法，取得 UserDetails 物件
			UserDetails userDetails = userService.loadUserByUsername(userEmail);
			
			// 驗證 Token 是否有效 
			if (jwtUtils.validateToken(jwt)) {
				
				// 建立一個認證物件 (Authentication Token)，包含使用者資訊和權限
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, 
						null, 
						userDetails.getAuthorities()
				);	
				
				// 把當前請求的細節 (如 IP) 設定進去
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// 把認證資訊塞進「上下文」 (SecurityContextHolder) 
				// 這樣後面的 @PreAuthorize("hasRole('ADMIN')") 才能抓到誰是管理員
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
			
			// 繼續執行後續的過濾器鏈
			filterChain.doFilter(request, response);
			
		}
	}
		
}
