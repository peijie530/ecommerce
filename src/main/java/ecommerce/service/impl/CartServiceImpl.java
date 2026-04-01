package ecommerce.service.impl;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ecommerce.dto.AddToCartRequest;
import ecommerce.dto.CartItemResponse;
import ecommerce.dto.CartResponse;
import ecommerce.entity.Cart;
import ecommerce.entity.CartItem;
import ecommerce.entity.Product;
import ecommerce.entity.User;
import ecommerce.repository.CartItemRepository;
import ecommerce.repository.CartRepository;
import ecommerce.service.CartService;
import ecommerce.service.ProductService;
import ecommerce.service.UserService;



@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductService productService;
	private final UserService userService;
	
	public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService, UserService userService) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productService = productService;
		this.userService = userService;
	}
	
	@Override
	@Transactional
	public void addToCart(AddToCartRequest request) {
		
		User currentUser = userService.getCurrentUser();
		
		Cart cart = cartRepository.findByUserId(currentUser.getId())
				.orElseGet(() -> {
					Cart newCart = new Cart(currentUser);
					return cartRepository.save(newCart);
				});
		
		Product product = productService.getProduct(request.getProductId());
		
		// 檢查庫存是否足夠
		if (product.getStock() < request.getQuantity()) {
			throw new IllegalArgumentException("庫存不足");
		}
		
		// 檢查購物車中是否已經有這個商品
		CartItem exisitingItem = null;
		for (CartItem item : cart.getItems()) {
			
			// 如果已經有這個商品，則更新數量
			if (item.getProduct().getId().equals(product.getId())) {
				exisitingItem = item;
				break;  // 找到了就跳出迴圈
			}
			
		}
		
		// 根據檢查結果執行動作
		if (exisitingItem != null) {
			// A 情況：車子裡已經有了 -> 直接把數量加上去
			exisitingItem.setQuantity(exisitingItem.getQuantity() + request.getQuantity());
		} else {
			// B 情況：車子裡還沒有 -> 建立一個新的 CartItem
			CartItem newItem = new CartItem(cart, product, request.getQuantity());
			// 用addItem 同步雙向關係
			cart.addItem(newItem);
		}
		
		// 儲存整台購物車
		cartRepository.save(cart);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public CartResponse getMyCart() {
		User currentUser = userService.getCurrentUser();
		
		// 取得購物車，如果沒有就回傳一台新的空購物車
		Cart cart = cartRepository.findByUserId(currentUser.getId())
				.orElse(new Cart(currentUser));
		
		// 把 Cart 轉換成 CartResponse
		List<CartItemResponse> itemResponses = cart.getItems().stream()
				.map(item -> new CartItemResponse(
						item.getId(),
						item.getProduct().getId(),
						item.getProduct().getName(),
						item.getProduct().getImageUrl(),
						item.getProduct().getPrice(),
						item.getQuantity()
				)).toList();
		return new CartResponse(itemResponses);
	}
	
	@Override
	@Transactional
	public void removeFromCart(Long itemId) {
		
		User currentUser = userService.getCurrentUser();
		
		CartItem cartItem = cartItemRepository.findById(itemId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該購物車項目"));
		
		// 確認這個項目是屬於目前使用者的購物車
		Long ownerId = cartItem.getCart().getUser().getId();
		
		if (!ownerId.equals(currentUser.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒有權限刪除這個項目");
		}
		
		cartItemRepository.delete(cartItem);
		
	}

}
