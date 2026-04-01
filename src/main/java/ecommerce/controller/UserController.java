package ecommerce.controller;

import ecommerce.dto.UserResponse;
import ecommerce.entity.UserRole;
import ecommerce.entity.UserStatus;
import ecommerce.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 查詢所有用戶（僅 admin 可用）
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
    	List<UserResponse> users = userService.findAll();
    	
        return ResponseEntity.ok(users);
    }

    // 查詢單一用戶
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
    	UserResponse user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    // 修改用戶狀態（啟用/停用）
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        UserResponse updatedUser = userService.updateStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    	
    }

    // 修改用戶角色
    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable Long id, @RequestParam UserRole role) {
        UserResponse updatedUser = userService.updateRole(id, role);
    	return ResponseEntity.ok(updatedUser);
    }
    
    // 刪除用戶
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
   
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    	userService.deleteUser(id);
    	return ResponseEntity.noContent().build();
	}
    
}