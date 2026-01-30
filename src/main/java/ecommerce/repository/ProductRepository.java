package ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	
}
