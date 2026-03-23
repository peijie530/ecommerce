package ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)  // 開啟自動監聽時間
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 200)
	private String name;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
	
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal price;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Version // 避免庫存被超賣
	private Long version = 0L;  // 給初始值0
	
	@CreatedDate  // Spring 會自動填入建立時間
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate  // Spring 每次更新會自動改時間
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	
	protected Product() {}
	
	public Product(String name, BigDecimal price, Integer stock, String imageUrl) {
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
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	
	public Long getVersion() {
		return version;
	}

    // 庫存操作（下單會用到）
    public void decreaseStock(int quantity) {
    	
    	//  IllegalArgumentException：用於「呼叫者提供的輸入不符合規範」 ex：quantity <= 0
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        
        // IllegalStateException：用於「物件當前狀態不允許操作」 ex：庫存不夠扣
        if (this.stock < quantity) throw new IllegalStateException("insufficient stock");
        this.stock -= quantity;
    }

	
}
