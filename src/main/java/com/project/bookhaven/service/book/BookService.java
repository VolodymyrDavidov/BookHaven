package com.project.bookhaven.service.book;

import com.project.bookhaven.dto.book.BookDto;
import com.project.bookhaven.dto.book.BookSearchParameters;
import com.project.bookhaven.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto updatedBookDto);

    List<BookDto> search(BookSearchParameters bookSearchParameters);
}
