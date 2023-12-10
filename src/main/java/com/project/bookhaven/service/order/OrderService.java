package com.project.bookhaven.service.order;

import com.project.bookhaven.dto.order.MakeAnOrderDto;
import com.project.bookhaven.dto.order.OrderDto;
import com.project.bookhaven.dto.order.OrderItemDto;
import com.project.bookhaven.dto.order.UpdateOrderStatusDto;
import com.project.bookhaven.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto makeOrder(User user, MakeAnOrderDto makeAnOrderDto, Pageable pageable);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusDto updateOrderStatusDto);

    List<OrderItemDto> getOrderItems(Long orderId, Pageable pageable);

    List<OrderDto> getOrderHistory(Long userId, Pageable pageable);

    OrderItemDto getOrderItem(Long orderId, Long itemId);
}
