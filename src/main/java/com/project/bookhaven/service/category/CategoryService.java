package com.project.bookhaven.service.category;

import com.project.bookhaven.dto.book.BookDtoWithoutCategoryIds;
import com.project.bookhaven.dto.category.CategoryDto;
import com.project.bookhaven.dto.category.CategoryRequestDto;
import com.project.bookhaven.dto.category.CreateCategoryRequestDto;
import com.project.bookhaven.model.Category;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    Category save(CategoryRequestDto categoryRequestDto);

    CategoryDto update(Long id, CreateCategoryRequestDto createCategoryRequestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoriesId(Long id);
}
