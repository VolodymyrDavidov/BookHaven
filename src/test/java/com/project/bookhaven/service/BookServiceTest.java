package com.project.bookhaven.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.project.bookhaven.dto.book.BookDto;
import com.project.bookhaven.dto.book.CreateBookRequestDto;
import com.project.bookhaven.exception.EntityNotFoundException;
import com.project.bookhaven.mapper.BookMapper;
import com.project.bookhaven.model.Book;
import com.project.bookhaven.repository.book.BookRepository;
import com.project.bookhaven.repository.category.CategoryRepository;
import com.project.bookhaven.service.book.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    public static final Long INVALID_ID = 100L;
    public static final Long VALID_ID = 1L;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Get book with valid ID")
    void findBookById_validId_returnsValidDto() {
        Book book = new Book();
        book.setAuthor("Test author");
        book.setTitle("Test title");
        book.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.findById(1L);

        assertThat(actual).isEqualTo(bookDto);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookMapper, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Get book with not existing ID")
    void findById_nonExistingId_throwsException() {
        when(bookRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(INVALID_ID))
                .isInstanceOf(EntityNotFoundException.class);
        verify(bookRepository, times(1)).findById(INVALID_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Delete book with valid ID")
    void deleteById_existingId() {
        when(bookRepository.existsById(VALID_ID)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(VALID_ID);

        bookService.deleteById(VALID_ID);

        verify(bookRepository, times(1)).existsById(VALID_ID);
        verify(bookRepository, times(1)).deleteById(VALID_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Delete book with not existing ID")
    void deleteById_nonExistingId_throwsException() {
        when(bookRepository.existsById(INVALID_ID)).thenReturn(false);

        assertThatThrownBy(() -> bookService.deleteById(INVALID_ID))
                .isInstanceOf(EntityNotFoundException.class);
        verify(bookRepository, times(1)).existsById(INVALID_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Updating a book with not existing ID")
    void update_nonExistingId_ShouldThrowEntityNotFoundException() {
        when(bookRepository.findById(INVALID_ID))
                .thenThrow(new EntityNotFoundException("Can't find book by id " + INVALID_ID));
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "Test title", "Test author", "978-0-06-112118-5",
                BigDecimal.valueOf(9.99), "Test description", "Test image", List.of(1L));

        EntityNotFoundException entityNotFoundException = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.update(INVALID_ID, requestDto)
        );

        assertEquals("Can't find book by id " + INVALID_ID,
                entityNotFoundException.getMessage());
        assertEquals(EntityNotFoundException.class, entityNotFoundException.getClass());
    }
}
