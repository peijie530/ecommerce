package ecommerce.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "categories", uniqueConstraints = {
		@UniqueConstraint(name = "uk_categories_name", columnNames = "name"),
		@UniqueConstraint(name = "uk_categories_slug", columnNames = "slug")
})
public class Category {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 120)
    private String slug;


    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    

    protected Category() {}

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
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
    public String getSlug() { 
    	return slug; 
    }

    public void setName(String name) { 
    	this.name = name; 
    }
    public void setSlug(String slug) { 
    	this.slug = slug; 
    }


}
