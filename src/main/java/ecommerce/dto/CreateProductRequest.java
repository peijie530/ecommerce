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

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
