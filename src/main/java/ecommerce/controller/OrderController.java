package ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.dto.CheckoutRequest;
import ecommerce.dto.OrderResponse;
import ecommerce.service.OrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	// 執行結帳 (建立訂單)
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CheckoutRequest request) {
		//  呼叫 Service 處理結帳：扣庫存	、存訂單、清購物車
		OrderResponse response = orderService.createOrder(request);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getMyOrders() {
		List<OrderResponse> orders = orderService.getUserOrders();
		return ResponseEntity.ok(orders);
	}
}
