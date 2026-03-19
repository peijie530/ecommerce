package ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PatchProductRequest {

	// 改用 @Size(min = 1)，這樣如果是 null (沒傳) 會跳過驗證
    // 但如果有傳，長度就不能為 0 (空字串)
	@Size(min = 1, message = "商品名稱不能為空")
	private String name;
	
	@Positive(message = "價格必須大於 0")
	private BigDecimal price;
	
	@Positive(message = "庫存必須大於 0")
	private Integer stock;
	
	private String imageUrl;
	
	public PatchProductRequest() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public String getImageUrl() {
    	return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
    	this.imageUrl = imageUrl;
    }
}
