package com.project.bookhaven.dto.shoppingcart;

import com.project.bookhaven.dto.cartitem.CartItemDto;
import java.util.List;

public record ShoppingCartDto(Long id, Long userId, List<CartItemDto> cartItems) {
}
