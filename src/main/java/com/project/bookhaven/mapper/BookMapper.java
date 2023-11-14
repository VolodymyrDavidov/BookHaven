package com.project.bookhaven.mapper;

import com.project.bookhaven.config.MapperConfig;
import com.project.bookhaven.dto.BookDto;
import com.project.bookhaven.dto.CreateBookRequestDto;
import com.project.bookhaven.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
