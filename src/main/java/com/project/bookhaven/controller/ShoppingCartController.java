package com.project.bookhaven.controller;

import com.project.bookhaven.dto.cartitem.CartItemDto;
import com.project.bookhaven.dto.cartitem.CartItemRequestDto;
import com.project.bookhaven.dto.cartitem.UpdateCartItemQuantity;
import com.project.bookhaven.dto.shoppingcart.ShoppingCartDto;
import com.project.bookhaven.model.User;
import com.project.bookhaven.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart management", description = "Endpoints for managing Shopping Cart")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = ShoppingCartController.CART_BASE_PATH)
public class ShoppingCartController {
    public static final String CART_BASE_PATH = "/api/cart";
    public static final String CART_ITEMS_PATH = "/cart-items/{cartItemId}";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AuthenticationPrincipal
    public @interface CurrentUser {
    }

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Retrieve user's shopping cart")
    public ShoppingCartDto getUserShoppingCart(@CurrentUser User user, Pageable pageable) {
        return shoppingCartService.getUserShoppingCart(user, pageable);
    }

    @PostMapping
    @Operation(summary = "Add item to the shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDto addItemToShoppingCart(@CurrentUser User user,
                                             @RequestBody @Valid CartItemRequestDto requestDto) {
        return shoppingCartService.addItemToShoppingCart(user, requestDto);
    }

    @PutMapping(value = "/cart-items/{cartItemId}")
    @Operation(summary = "Update quantity of a book in the shopping cart")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartItemDto updateQuantity(
            @CurrentUser User user,
            @PathVariable @Min(1) Long cartItemId,
            @RequestBody @Valid UpdateCartItemQuantity requestDto) {
        return shoppingCartService.updateCartItemQuantity(user, cartItemId, requestDto);
    }

    @DeleteMapping(CART_ITEMS_PATH)
    @Operation(summary = "Remove a book from the shopping cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemFromShoppingCart(@CurrentUser User user,
                                           @PathVariable Long cartItemId) {
        shoppingCartService.deleteItemFromShoppingCart(user, cartItemId);
    }
}
