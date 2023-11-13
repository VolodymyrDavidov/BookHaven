package com.project.bookhaven.service;

import com.project.bookhaven.dto.BookDto;
import com.project.bookhaven.dto.CreateBookRequestDto;
import com.project.bookhaven.exception.EntityNotFoundException;
import com.project.bookhaven.mapper.BookMapper;
import com.project.bookhaven.model.Book;
import com.project.bookhaven.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id:" + id));
        return bookMapper.toDto(book);
    }

}
