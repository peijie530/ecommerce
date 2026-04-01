package ecommerce.service;

import ecommerce.dto.AddToCartRequest;
import ecommerce.dto.CartResponse;

public interface CartService {
	
	// 加入商品到購物車
	void addToCart(AddToCartRequest request);
	
	// 取得目前登入使用者的購物車內容
	CartResponse getMyCart();
	
	// 從購物車移除商品
	void removeFromCart(Long itemId);

}
