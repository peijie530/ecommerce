package ecommerce.entity;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "cart")
@EntityListeners(AuditingEntityListener.class)
public class Cart {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne    // 每個用戶只有一個購物車
	@JoinColumn(name = "user_id", nullable = false)  // 外鍵關聯到用戶表
	private User user;
	
	// mappedBy = "cart"：告訴 JPA，這段關係的詳細規則寫在 CartItem 那一端的 cart 欄位裡
	// cascade = CascadeType.ALL：當 Cart 被刪除時，相關的 CartItem 也會被刪除
	// orphanRemoval = true：如果 CartItem 不再被 Cart 參考（例如從 items 列表中移除），則該 CartItem 也會被刪除
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)  
	private List<CartItem> items = new ArrayList<>();  // 購物車中的商品列表
	
	@CreatedDate
	private LocalDateTime createdAt;  
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	
	public Cart() {
	}
	
	public Cart(User user) {
	    this.user = user;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<CartItem> getItems() {
		return items;
	}
	
	// 同步雙向關係
	public void addItem(CartItem item) {
	    this.items.add(item); // Cart 端知道有這個 Item
	    item.setCart(this);   // Item 端也要知道屬於哪個 Cart，存檔時 cart_id 才有值
	}
	
	
	
}
