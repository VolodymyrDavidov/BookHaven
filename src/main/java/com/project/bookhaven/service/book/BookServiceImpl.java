package com.project.bookhaven.service.book;

import com.project.bookhaven.dto.book.BookDto;
import com.project.bookhaven.dto.book.BookDtoWithoutCategoryIds;
import com.project.bookhaven.dto.book.BookSearchParameters;
import com.project.bookhaven.dto.book.CreateBookRequestDto;
import com.project.bookhaven.exception.EntityNotFoundException;
import com.project.bookhaven.mapper.BookMapper;
import com.project.bookhaven.model.Book;
import com.project.bookhaven.model.Category;
import com.project.bookhaven.repository.book.BookRepository;
import com.project.bookhaven.repository.book.BookSpecificationBuilder;
import com.project.bookhaven.repository.category.CategoryRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);
        setCategories(bookRequestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id:" + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto updatedBookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id:" + id));
        Book book = bookMapper.toModel(updatedBookDto);
        setCategories(updatedBookDto, book);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(existingBook));
    }

    @Override
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Pageable pageable, Long id) {
        return bookRepository.findAllByCategory(pageable, id)
                .stream()
                .map(bookMapper::toDtoWithoutCategoryIds)
                .toList();
    }

    private void setCategories(CreateBookRequestDto requestDto, Book book) {
        Set<Category> categorySet = requestDto.categoryIds().stream()
                .map(categoryRepository::getReferenceById)
                .collect(Collectors.toSet());
        book.setCategories(categorySet);
    }
}
