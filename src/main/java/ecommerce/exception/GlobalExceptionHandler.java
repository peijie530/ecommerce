package ecommerce.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // 攔截全專案 Controller 噴出的異常，是 @ControllerAdvice 的加強版，會自動把回傳值轉成 JSON
public class GlobalExceptionHandler {
	
	// 專門處理「權限不足」 (403 Forbidden)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.FORBIDDEN.value());
		body.put("error", "Forbidden");
		body.put("message", "抱歉，您的權限不足，無法執行此操作。");
		body.put("path", "此 API 需要管理員權限");
		
		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
		
	}
	
	// 處理所有未預期的錯誤 (500 Error)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneralEntity(Exception ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		body.put("message", "系統發生預期外的錯誤:" + ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
