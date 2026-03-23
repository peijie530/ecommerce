package ecommerce.controller;

import ecommerce.dto.UserResponse;
import ecommerce.entity.UserRole;
import ecommerce.entity.UserStatus;
import ecommerce.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 查詢所有用戶（僅 admin 可用）
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    // 查詢單一用戶
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // 修改用戶狀態（啟用/停用）
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUserStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        return userService.updateStatus(id, status);
    }

    // 修改用戶角色
    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUserRole(@PathVariable Long id, @RequestParam UserRole role) {
        return userService.updateRole(id, role);
    }
    
    // 刪除用戶
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 成功刪除回傳 204
    public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
    
}