package ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateProductRequest {

    @NotBlank  // 不能是 null / 空字串
    private String name;

    @NotNull
    @Positive  // 必須 > 0
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer stock;
    
    public CreateProductRequest() {} // 可寫可不寫（沒寫也會有預設的）

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
