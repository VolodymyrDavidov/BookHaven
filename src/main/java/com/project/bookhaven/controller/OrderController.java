package com.project.bookhaven.controller;

import com.project.bookhaven.annotation.CurrentUser;
import com.project.bookhaven.dto.order.MakeAnOrderDto;
import com.project.bookhaven.dto.order.OrderDto;
import com.project.bookhaven.dto.order.OrderItemDto;
import com.project.bookhaven.dto.order.UpdateOrderStatusDto;
import com.project.bookhaven.model.User;
import com.project.bookhaven.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping(OrderController.ORDER_BASE_PATH)
public class OrderController {
    public static final String ORDER_BASE_PATH = "/api/orders";

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place an order")
    public OrderDto placeOrder(
            @CurrentUser User user,
            @RequestBody @Valid MakeAnOrderDto requestDto,
            @ParameterObject Pageable pageable
    ) {
        return orderService.makeOrder(user, requestDto, pageable);
    }

    @GetMapping
    @Operation(summary = "Retrieve user's order history")
    public List<OrderDto> getOrderHistory(@CurrentUser User user,
                                          @ParameterObject Pageable pageable) {
        return orderService.getOrderHistory(user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Retrieve all OrderItems for a specific order")
    public List<OrderItemDto> getOrderItems(
            @PathVariable @Min(1) Long orderId,
            @ParameterObject Pageable pageable
    ) {
        return orderService.getOrderItems(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Retrieve a specific OrderItem within an order")
    public OrderItemDto getOrderItem(
            @PathVariable @Min(1) Long orderId,
            @PathVariable @Min(1) Long itemId
    ) {
        return orderService.getOrderItem(orderId, itemId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update order status")
    public OrderDto updateOrderStatus(
            @PathVariable @Min(1) Long id,
            @RequestBody UpdateOrderStatusDto request
    ) {
        return orderService.updateOrderStatus(id, request);
    }
}
