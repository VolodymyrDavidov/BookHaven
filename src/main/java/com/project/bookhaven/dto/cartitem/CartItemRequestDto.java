package com.project.bookhaven.dto.cartitem;

import jakarta.validation.constraints.Min;

public record CartItemRequestDto(
        @Min(1) Long bookId,
        @Min(1) int quantity) {
}
