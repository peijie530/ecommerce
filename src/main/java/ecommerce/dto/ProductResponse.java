package ecommerce.dto;

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;

    public ProductResponse(Long id, String name, BigDecimal price, Integer stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
    
    public String getImageUrl() {
		return imageUrl;
	}


}
