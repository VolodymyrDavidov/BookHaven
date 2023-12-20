package com.project.bookhaven.dto.category;

import jakarta.validation.constraints.NotNull;

public record CategoryRequestDto(
        @NotNull(message = "Name cannot be null")
        String name,
        String description) {
}
