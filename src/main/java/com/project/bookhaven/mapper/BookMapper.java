package com.project.bookhaven.mapper;

import com.project.bookhaven.config.MapperConfig;
import com.project.bookhaven.dto.book.BookDto;
import com.project.bookhaven.dto.book.BookDtoWithoutCategoryIds;
import com.project.bookhaven.dto.book.CreateBookRequestDto;
import com.project.bookhaven.model.Book;
import com.project.bookhaven.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .toList());
    }
}
