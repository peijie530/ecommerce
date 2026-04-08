package ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
