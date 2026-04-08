package ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

	private Long id;
	private String orderNo;
	private BigDecimal totalPrice;
	private String status;
	private LocalDateTime createdAt;
	
	public OrderResponse(Long id, String orderNo, BigDecimal totalPrice, String status, LocalDateTime createdAt) {
		this.id = id;
		this.orderNo = orderNo;
		this.totalPrice = totalPrice;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	public Long getId() {
		return id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public String getStatus() {
		return status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
