package ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddToCartRequest {

	@NotNull(message = "商品 ID 不能為空")
	private Long productId;
	
	@NotNull(message = "數量不能為空")
	@Min(value = 1, message = "數量必須至少為 1")
	private Integer quantity = 1;  // 預設數量為 1
	
	public AddToCartRequest() {
	}
	
	public AddToCartRequest(Long productId, Integer quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
