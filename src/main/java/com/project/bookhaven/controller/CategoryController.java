package com.project.bookhaven.controller;

import com.project.bookhaven.dto.book.BookDtoWithoutCategoryIds;
import com.project.bookhaven.dto.category.CategoryDto;
import com.project.bookhaven.dto.category.CategoryRequestDto;
import com.project.bookhaven.dto.category.CreateCategoryRequestDto;
import com.project.bookhaven.mapper.CategoryMapper;
import com.project.bookhaven.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Endpoints for managing categories")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Get all categories", description = "Get all categories with pagination")
    @GetMapping
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable).stream().toList();
    }

    @Operation(summary = "Get category", description = "Get a category by ID")
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Get book by category Id", description = "Get book by category Id")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id) {
        return categoryService.getBooksByCategoriesId(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create new category", description = "Create a new category (Admin)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryMapper.toDto(categoryService.save(categoryRequestDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update category", description = "Update a category by ID (Admin)")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody
                                      @Valid CreateCategoryRequestDto updatedCategoryDto) {

        return categoryService.update(id, updatedCategoryDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete category", description = "Delete a category by ID (Admin)")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
