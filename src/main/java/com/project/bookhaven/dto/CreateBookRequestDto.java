package com.project.bookhaven.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    private String author;
    @NotEmpty
    @Pattern(regexp = "^(978|979)-\\d{1,5}-\\d{2,7}-\\d{1,6}-[\\dX]$")
    private String isbn;
    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    private BigDecimal price;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotBlank(message = "CoverImage cannot be blank")
    private String coverImage;
}
