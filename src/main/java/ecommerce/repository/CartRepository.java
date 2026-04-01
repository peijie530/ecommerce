package ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ecommerce.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	// 根據 User 找購物車
    Optional<Cart> findByUserId(Long userId);
}
