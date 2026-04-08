package ecommerce.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.dto.CheckoutRequest;
import ecommerce.dto.OrderResponse;
import ecommerce.entity.User;
import ecommerce.entity.Cart;
import ecommerce.entity.CartItem;
import ecommerce.entity.Order;
import ecommerce.entity.OrderItem;
import ecommerce.entity.Product;
import ecommerce.repository.CartRepository;
import ecommerce.repository.OrderRepository;
import ecommerce.service.OrderService;
import ecommerce.service.UserService;

@Service
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final UserService userService;
	
	public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, UserService userService) {
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.userService = userService;
	}
	
	@Override
	@Transactional
	public OrderResponse createOrder(CheckoutRequest request) {
		// 取得目前使用者
		User currentUser = userService.getCurrentUser();
		
		// 取得購物車
		Cart cart = cartRepository.findByUserId(currentUser.getId())
				.orElseThrow(() -> new IllegalStateException("找不到購物車"));
		
		if (cart.getItems().isEmpty()) {
			throw new IllegalStateException("購物車是空的，無法結帳");
		}
		
		// 建立 Order 實體
		String orderNo = generateOrderNo();
		Order order = new Order(currentUser, orderNo, cart.calculateTotalPrice(), "PENDING");
		
		// 設定地址資訊
		order.setReceiverName(request.getReceiverName());
		order.setReceiverPhone(request.getReceiverPhone());
		order.setShippingLine1(request.getShippingLine1());
		order.setShippingLine2(request.getShippingLine2());
		order.setShippingCity(request.getShippingCity());
		order.setShippingPostalCode(request.getShippingPostalCode());
		
		// 將 CartItem 轉換為 OrderItem，並扣除庫存
		for (CartItem cartItem : cart.getItems()) {
			Product product = cartItem.getProduct();
			
			// 呼叫在 Product 實作的庫存防護邏輯
			product.decreaseStock(cartItem.getQuantity());
			
			// 建立明細並記下當時價格
			OrderItem orderItem = new OrderItem(product, cartItem.getQuantity(), product.getPrice());
			order.addItem(orderItem);  // 使用雙向關聯方法
		}
		
		// 儲存訂單（會同時儲存 OrderItem）
		Order savedOrder = orderRepository.save(order);
		
		// 清空購物車
		cart.getItems().clear();  // 觸發 orphanRemoval 刪除資料庫中的 CartItem
		cartRepository.save(cart);  // 更新購物車狀態
		
		return new OrderResponse(
				savedOrder.getId(), 
				savedOrder.getOrderNo(), 
				savedOrder.getTotalPrice(), 
				savedOrder.getStatus(),
				LocalDateTime.now()
		);
		
		
		
	}
	
	// 產生唯一的訂單編號
	private String generateOrderNo() {
		// 把時間轉成指定格式的字串，再加上 3 位隨機數，確保訂單編號的唯一性
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
				+ (int)(Math.random() * 900 + 100);  // 產生 100~999 的隨機數
	}
	
	// 查詢個人訂單
	@Override
	public List<OrderResponse> getUserOrders() {
		
		// 1. 從 SecurityContext 取得目前登入的使用者
		User currentUser = userService.getCurrentUser();
		
		// 2. 從資料庫查詢該使用者的訂單，並轉換成 OrderResponse
		List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
		
		// 3. 將 Order 實體轉換成 OrderResponse DTO，並回傳給前端
		return orders.stream()
				.map(order -> new OrderResponse(
						order.getId(), 
						order.getOrderNo(), 
						order.getTotalPrice(), 
						order.getStatus(), 
						order.getCreatedAt()
		)).collect(Collectors.toList());
	}

}
