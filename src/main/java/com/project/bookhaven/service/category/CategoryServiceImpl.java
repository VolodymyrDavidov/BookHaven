package com.project.bookhaven.service.category;

import com.project.bookhaven.dto.book.BookDtoWithoutCategoryIds;
import com.project.bookhaven.dto.category.CategoryDto;
import com.project.bookhaven.dto.category.CategoryRequestDto;
import com.project.bookhaven.exception.EntityNotFoundException;
import com.project.bookhaven.mapper.BookMapper;
import com.project.bookhaven.mapper.CategoryMapper;
import com.project.bookhaven.model.Book;
import com.project.bookhaven.model.Category;
import com.project.bookhaven.repository.category.CategoryRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream().map(categoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find category by id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public Category save(CategoryRequestDto categoryRequestDto) {
        return categoryRepository.save(categoryMapper.toModel(categoryRequestDto));
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto updateCategoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find category by id:" + id));
        categoryMapper.updateFromDto(updateCategoryDto, existingCategory);
        Category updetingCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDto(updetingCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoriesId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find category by id: " + id));

        Set<Book> books = category.getBooksSet();
        return books.stream()
                .map(bookMapper::toDtoWithoutCategoryIds)
                .collect(Collectors.toList());
    }
}
