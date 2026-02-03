package ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class PatchProductRequest {

	// PATCH 的欄位可以是 null（代表不更新）
	
	@NotBlank  // 對 null 視為通過，但對空字串會不通過（這符合 patch 需求）
	private String name;
	
	@Positive  // 對 null 不會驗證，只有有值時才驗證
	private BigDecimal price;
	
	@Positive 
	private Integer stock;
	
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
}
