package com.project.bookhaven.service.order;

import com.project.bookhaven.dto.order.MakeAnOrderDto;
import com.project.bookhaven.dto.order.OrderDto;
import com.project.bookhaven.dto.order.OrderItemDto;
import com.project.bookhaven.dto.order.UpdateOrderStatusDto;
import com.project.bookhaven.exception.EntityNotFoundException;
import com.project.bookhaven.mapper.OrderItemMapper;
import com.project.bookhaven.mapper.OrderMapper;
import com.project.bookhaven.model.CartItem;
import com.project.bookhaven.model.Order;
import com.project.bookhaven.model.OrderItem;
import com.project.bookhaven.model.ShoppingCart;
import com.project.bookhaven.model.User;
import com.project.bookhaven.repository.cartitem.CartItemRepository;
import com.project.bookhaven.repository.order.OrderRepository;
import com.project.bookhaven.repository.shoppingcart.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public OrderDto makeOrder(User user, MakeAnOrderDto makeAnOrderDto, Pageable pageable) {
        ShoppingCart shoppingCart = getShoppingCart(user);
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new EntityNotFoundException("Shopping Cart is empty");
        }
        Order order = createOrder(user, makeAnOrderDto, shoppingCart);
        orderRepository.save(order);
        cartItemRepository.deleteCartItemsByShoppingCart(shoppingCart.getId());
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusDto updateOrderStatusDto) {
        Order order = getOrderFromDb(orderId);
        Order.OrderStatus newStatus = orderStatusFromRequest(updateOrderStatusDto);
        order.setOrderStatus(newStatus);
        orderRepository.save(order);
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId, Pageable pageable) {
        Order order = getOrderFromDb(orderId);
        return order.getOrderItems().stream()
                .map(orderItemMapper::orderItemToDto)
                .toList();
    }

    @Override
    public List<OrderDto> getOrderHistory(Long userId, Pageable pageable) {
        return orderRepository.findById(userId)
                .stream()
                .map(orderMapper::orderToOrderDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long itemId) {
        Order order = getOrderFromDb(orderId);
        return order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(itemId))
                .findFirst()
                .map(orderItemMapper::orderItemToDto)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order item with id: "
                        + itemId + " in order id: " + orderId));
    }

    private ShoppingCart getShoppingCart(User user) {
        return shoppingCartRepository.getShoppingCartByUserEmail(user.getEmail());
    }

    private Order createOrder(User user, MakeAnOrderDto requestDto, ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());
        order.setOrderStatus(Order.OrderStatus.NEW);
        Set<OrderItem> orderItems = createOrderItems(order, shoppingCart.getCartItems());
        BigDecimal total = calculateTotal(orderItems);
        order.setOrderItems(orderItems);
        order.setTotal(total);
        return order;
    }

    private Set<OrderItem> createOrderItems(Order order, Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> createOrderItemFromCartItem(order, cartItem))
                .collect(Collectors.toSet());
    }

    private OrderItem createOrderItemFromCartItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order.OrderStatus orderStatusFromRequest(UpdateOrderStatusDto request) {
        return Order.OrderStatus.fromValue(request.status()).orElseThrow(
                () -> new EntityNotFoundException("Invalid status: " + request.status()));
    }

    private Order getOrderFromDb(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by Id: " + orderId)
        );
    }
}
