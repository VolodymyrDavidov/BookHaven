package com.project.bookhaven.service.shoppingcart;

import com.project.bookhaven.dto.cartitem.CartItemDto;
import com.project.bookhaven.dto.cartitem.CartItemRequestDto;
import com.project.bookhaven.dto.cartitem.UpdateCartItemQuantity;
import com.project.bookhaven.dto.shoppingcart.ShoppingCartDto;
import com.project.bookhaven.model.User;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    CartItemDto addItemToShoppingCart(User user, CartItemRequestDto requestDto);

    ShoppingCartDto getShoppingCart(User user, Pageable pageable);

    CartItemDto updateCartItemQuantity(User user, Long cartItemId,
                                       UpdateCartItemQuantity updateDto);

    void deleteItemFromShoppingCart(User user, Long cartItemId);
}
