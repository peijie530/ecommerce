package ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

	@NotBlank(message = "email不能為空")
	@Email(message = "email格式不正確")
	private String email;
	
	@NotBlank(message = "密碼不能為空")
	@Size(min = 6, message = "密碼長度至少需要 6 位")
	private String password;
	
	@NotBlank(message = "姓名不能為空")
	private String name;
	
	// 無參數建構子 (Jackson 反序列化需要)
    public RegisterRequest() {}
    
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
}
