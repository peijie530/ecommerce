package ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;


// 購物車整體的狀態
public class CartResponse {
	
	private List<CartItemResponse> items;
	
	private BigDecimal totalPrice;   // 整台購物車的總價
	
	public CartResponse() {
	}
	
	public CartResponse(List<CartItemResponse> items) {
		this.items = items;
		// 計算總金額：把所有項目的 subtotal 加總
        this.totalPrice = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	
	public List<CartItemResponse> getItems() {
		return items;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
}
