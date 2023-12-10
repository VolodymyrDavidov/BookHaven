package com.project.bookhaven.repository.orderitem;

import com.project.bookhaven.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderIdAndId(Long orderId, Long itemId);
}
