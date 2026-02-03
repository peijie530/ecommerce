package ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.IllegalFormatCodePointException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "products")
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
	private Long version;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	// JPA needs a no-args constructor
	protected Product() {}
	
	public Product(String name, BigDecimal price, Integer stock) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.price = price;
		this.stock = stock;
	}
	
	@PrePersist
	void onCreate() {
		this.createdAt = this.updatedAt = LocalDateTime.now();
		if (this.version == null) {
			this.version = 0L;
		}
	}
	
	@PreUpdate
	void onUpdate() {
		this.updatedAt = LocalDateTime.now();
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

    // 庫存操作（之後下單會用到）
    public void decreaseStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        if (this.stock < quantity) throw new IllegalStateException("insufficient stock");
        this.stock -= quantity;
    }

	
}
