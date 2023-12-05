package com.project.bookhaven.mapper;

import com.project.bookhaven.config.MapperConfig;
import com.project.bookhaven.dto.category.CategoryDto;
import com.project.bookhaven.dto.category.CategoryRequestDto;
import com.project.bookhaven.dto.category.CreateCategoryRequestDto;
import com.project.bookhaven.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryRequestDto categoryDto);

    void updateFromDto(CreateCategoryRequestDto requestDto, @MappingTarget Category category);
}
