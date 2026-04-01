package ecommerce.dto;

import java.math.BigDecimal;

//每一列商品的詳細資訊
public class CartItemResponse {

	private Long itemId;      // CartItem 的 ID (方便刪除用)
	
	private Long productId;   // 商品 ID
	
	private String productName;
	
	private String imageUrl;  
	
	private BigDecimal unitPrice;  // 單價
	
	private Integer quantity;
	
	private BigDecimal subtotal;  // 單項小計 (unitPrice * quantity)
	
	public CartItemResponse() {
	}
	
	public CartItemResponse(Long itemId, Long productId, String productName, String imageUrl, BigDecimal unitPrice,
			Integer quantity) {
		this.itemId = itemId;
		this.productId = productId;
		this.productName = productName;
		this.imageUrl = imageUrl;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		// 自動計算小計
        this.subtotal = unitPrice.multiply(new BigDecimal(quantity));
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	
	public Long getProductId() {
		return productId;
	}
	
	
	public String getProductName() {
		return productName;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	
	
}
