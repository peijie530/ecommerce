package ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.dto.AddToCartRequest;
import ecommerce.dto.CartResponse;
import ecommerce.service.CartService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

	private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	// 加入商品到購物車
	@PostMapping("/items")
	public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartRequest request) {
		cartService.addToCart(request);
		return ResponseEntity.ok("商品已成功加入購物車！");
	}
	
	// 取得目前的購物車內容
	@GetMapping 
	public ResponseEntity<CartResponse> getMyCart() {
		CartResponse cart = cartService.getMyCart();
		return ResponseEntity.ok(cart);
	}
	
	// 從購物車刪除某個項目
	@DeleteMapping("/items/{itemId}")
	public ResponseEntity<Void> removeFromCart(@PathVariable Long itemId) {
		cartService.removeFromCart(itemId);
		return ResponseEntity.noContent().build();
	}
	
}
