package ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)  // 開啟自動監聽時間
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "order_no", nullable = false, unique = true, length = 30)
	private String orderNo;  // 訂單編號，唯一且不可為空
	
	
	@ManyToOne(fetch = FetchType.LAZY)  // 多個訂單對一個使用者   LAZY用到才查資料庫
	@JoinColumn(name = "user_id", nullable = false) // 指定外鍵欄位
	private User user;  // 訂單所屬的使用者
	
	@Column(name = "total_price", nullable = false, precision = 12, scale = 2)
	private BigDecimal totalPrice;  // 訂單總金額
	
	@Column(nullable = false, length = 20)
	private String status;  // 訂單狀態，例如 "PENDING", "COMPLETED", "CANCELLED"
	
	// 收件資訊
	@Column(name = "receiver_name", length = 100)
	private String receiverName;  // 收件人姓名
	
	@Column(name = "receiver_phone", length = 30)
	private String receiverPhone; // 收件人電話
	
	@Column(name = "shipping_line1", length = 200)
	private String shippingLine1; // 收件地址第一行
	
	@Column(name = "shipping_line2", length = 200)
	private String shippingLine2; // 收件地址第二行	
	
	@Column(name = "shipping_city", length = 100)
	private String shippingCity;  // 收件城市
	
	@Column(name = "shipping_postal_code", length = 20)
	private String shippingPostalCode; // 收件郵遞區號
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
	
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)  // 一個訂單有很多 order items
    // cascade = ALL — CRUD 一起做
    // orphanRemoval = true — 從 list 移除就自動刪除資料庫
    private List<OrderItem> items = new ArrayList<>();  // 訂單中的項目列表
    
	protected Order() {}
	
	public Order(User user, String orderNo, BigDecimal totalPrice, String status) {
        this.user = user;
        this.orderNo = orderNo;
        this.totalPrice = totalPrice;
        this.status = status;
    }
	
	// 同步雙向關係的方法
    public void addItem(OrderItem item) {
        items.add(item);       // 訂單覺得有 item
        item.setOrder(this);  // item 也覺得自己屬於這張訂單
    }
	
	public Long getId() {
		return id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public User getUser() {
		return user;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	
	// 收件人資訊的 Getter/Setter
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	
	public String getShippingLine1() {
		return shippingLine1;
	}
	
	public void setShippingLine1(String shippingLine1) {
		this.shippingLine1 = shippingLine1;
	}
	
	public String getShippingLine2() {
		return shippingLine2;
	}
	
	public void setShippingLine2(String shippingLine2) {
		this.shippingLine2 = shippingLine2;
	}
	
	public String getShippingCity() {
		return shippingCity;
	}
	
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	
	public String getShippingPostalCode() {
		return shippingPostalCode;
	}
	
	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}
	
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
}
