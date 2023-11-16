package com.project.bookhaven.service;

import com.project.bookhaven.dto.BookDto;
import com.project.bookhaven.dto.BookSearchParameters;
import com.project.bookhaven.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto updatedBookDto);

    List<BookDto> search(BookSearchParameters bookSearchParameters);
}
