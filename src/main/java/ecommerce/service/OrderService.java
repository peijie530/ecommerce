package ecommerce.service;

import java.util.List;

import ecommerce.dto.CheckoutRequest;
import ecommerce.dto.OrderResponse;

public interface OrderService {

	// 執行結帳流程：驗證 -> 扣庫存 -> 產生訂單 -> 清空購物車
	OrderResponse createOrder(CheckoutRequest request); 
	
	// 查詢個人訂單
	List<OrderResponse> getUserOrders();
}
