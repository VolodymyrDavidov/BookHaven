package com.project.bookhaven.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.project.bookhaven.dto.category.CategoryDto;
import com.project.bookhaven.dto.category.CategoryRequestDto;
import com.project.bookhaven.exception.EntityNotFoundException;
import com.project.bookhaven.mapper.CategoryMapper;
import com.project.bookhaven.mapper.impl.CategoryMapperImpl;
import com.project.bookhaven.model.Category;
import com.project.bookhaven.repository.category.CategoryRepository;
import com.project.bookhaven.service.category.CategoryServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    public static final Long INVALID_ID = 100L;
    public static final Long VALID_ID = 1L;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Spy
    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    @DisplayName("Save new category with valid Dto")
    void save_validRequestDto_returnsDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto("Test category", "Test description");

        Category category = new Category();
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());

        when(categoryRepository.save(category)).thenReturn(category);

        Category actual = categoryService.save(requestDto);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description());
    }

    @Test
    @DisplayName("Update valid category with valid Dto")
    void update_validRequestDto_returnsDto() {

        Category category = new Category();
        category.setName("dramatic");
        category.setId(VALID_ID);

        CategoryRequestDto requestDto = new CategoryRequestDto("Test category", "Test description");

        Category requestCategory = new Category();
        requestCategory.setId(VALID_ID);
        requestCategory.setName(requestDto.name());
        requestCategory.setDescription(requestDto.description());

        CategoryDto categoryDto = new CategoryDto(1L,
                requestCategory.getName(),
                requestCategory.getDescription());

        when(categoryRepository.findById(VALID_ID)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(requestCategory);

        CategoryDto actual = categoryService.update(VALID_ID, requestDto);

        assertThat(actual).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Update not existing category")
    void update_notExistingRequestDto_throwsException() {
        CategoryRequestDto requestDto = new CategoryRequestDto("Test category", "Test description");

        when(categoryRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.update(INVALID_ID, requestDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Get category by valid id")
    void getById_existingId_returnsValidDto() {
        Category category = new Category();
        category.setName("dramatic");
        category.setId(1L);

        CategoryDto categoryDto = new CategoryDto(category.getId(),
                category.getName(),
                category.getDescription());

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryDto actual = categoryService.getById(1L);

        assertThat(actual).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Get category by not existing id")
    void getById_notExistingId_throwsException() {
        when(categoryRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getById(INVALID_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Delete category by valid id")
    void deleteById_existingId() {
        doNothing().when(categoryRepository).deleteById(VALID_ID);

        categoryService.deleteById(VALID_ID);

        verify(categoryRepository, times(1)).deleteById(VALID_ID);
        verifyNoMoreInteractions(categoryRepository);
    }
}
