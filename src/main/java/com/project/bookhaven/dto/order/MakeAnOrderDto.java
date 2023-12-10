package com.project.bookhaven.dto.order;

import jakarta.validation.constraints.NotEmpty;

public record MakeAnOrderDto(@NotEmpty String shippingAddress) {
}
