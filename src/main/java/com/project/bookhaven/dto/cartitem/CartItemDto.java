package com.project.bookhaven.dto.cartitem;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity) {

}
