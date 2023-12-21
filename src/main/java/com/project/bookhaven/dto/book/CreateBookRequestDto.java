package com.project.bookhaven.dto.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(
        @NotBlank(message = "Title cannot be blank") String title,
        @NotBlank(message = "Author cannot be blank") String author,
        @NotEmpty @Pattern(regexp = "^(978|979)-\\d{1,5}-\\d{2,7}-\\d{1,6}-[\\dX]$") String isbn,
        @Positive(message = "Price must be positive")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal price,
        @NotBlank(message = "Description cannot be blank") String description,
        @NotBlank(message = "CoverImage cannot be blank") String coverImage,
        @NotEmpty List<Long> categoryIds
) {
}
