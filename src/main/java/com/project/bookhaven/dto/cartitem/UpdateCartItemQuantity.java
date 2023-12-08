package com.project.bookhaven.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateCartItemQuantity(@NotNull @Positive int quantity) {
}
