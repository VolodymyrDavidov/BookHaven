package com.project.bookhaven.mapper;

import com.project.bookhaven.config.MapperConfig;
import com.project.bookhaven.dto.book.BookDto;
import com.project.bookhaven.dto.book.CreateBookRequestDto;
import com.project.bookhaven.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);
}
