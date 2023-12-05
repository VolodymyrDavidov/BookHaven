package com.project.bookhaven.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    private String description;
}
