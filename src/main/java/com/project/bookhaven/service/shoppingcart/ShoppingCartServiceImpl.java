package com.project.bookhaven.service.shoppingcart;

import com.project.bookhaven.dto.cartitem.CartItemDto;
import com.project.bookhaven.dto.cartitem.CartItemRequestDto;
import com.project.bookhaven.dto.cartitem.UpdateCartItemQuantity;
import com.project.bookhaven.dto.shoppingcart.ShoppingCartDto;
import com.project.bookhaven.exception.EntityNotFoundException;
import com.project.bookhaven.mapper.CartItemMapper;
import com.project.bookhaven.mapper.ShoppingCartMapper;
import com.project.bookhaven.model.Book;
import com.project.bookhaven.model.CartItem;
import com.project.bookhaven.model.ShoppingCart;
import com.project.bookhaven.model.User;
import com.project.bookhaven.repository.book.BookRepository;
import com.project.bookhaven.repository.cartitem.CartItemRepository;
import com.project.bookhaven.repository.shoppingcart.ShoppingCartRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CartItemDto addItemToShoppingCart(User user, CartItemRequestDto cartItemRequestDto) {
        Book book = getBook(cartItemRequestDto);
        ShoppingCart shoppingCart = getShoppingCart(user);
        Set<Long> bookIds = shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getBook().getId())
                .collect(Collectors.toSet());
        CartItem cartItem = getCartItem(cartItemRequestDto, bookIds, shoppingCart, book);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public ShoppingCartDto getUserShoppingCart(User user, Pageable pageable) {
        ShoppingCart shoppingCart = getShoppingCart(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public CartItemDto updateCartItemQuantity(User user, Long cartItemId,
                                              UpdateCartItemQuantity updateDto) {
        ShoppingCart shoppingCart = getShoppingCart(user);
        CartItem cartItem = findCartItem(shoppingCart, cartItemId);
        cartItem.setQuantity(updateDto.quantity());
        cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public void deleteItemFromShoppingCart(User user, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCart(user);
        CartItem cartItem = findCartItem(shoppingCart, cartItemId);
        cartItemRepository.delete(cartItem);
    }

    private CartItem findCartItem(ShoppingCart shoppingCart, Long cartItemId) {
        return shoppingCart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"
                        + " for book Id: " + cartItemId));
    }

    private ShoppingCart getShoppingCart(User user) {
        return shoppingCartRepository
                .getShoppingCartByUserEmail(user.getEmail());
    }

    private Book getBook(CartItemRequestDto cartItemRequestDto) {
        return bookRepository.findById(cartItemRequestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by Id "
                        + cartItemRequestDto.bookId()));
    }

    private CartItem getCartItem(
            CartItemRequestDto cartItemRequestDto,
            Set<Long> bookIds,
            ShoppingCart shoppingCart,
            Book book) {
        boolean containsBookId = bookIds.contains(cartItemRequestDto.bookId());
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        if (containsBookId) {
            CartItem existingCartItem = findCartItem(
                    shoppingCart, book.getId());
            existingCartItem.setQuantity(existingCartItem.getQuantity()
                    + cartItemRequestDto.quantity());
            cartItem = existingCartItem;
        } else {
            cartItem.setQuantity(cartItemRequestDto.quantity());
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setBook(book);
        }
        return cartItem;
    }
}
