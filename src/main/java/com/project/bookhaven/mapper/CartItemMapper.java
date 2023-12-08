package com.project.bookhaven.mapper;

import com.project.bookhaven.config.MapperConfig;
import com.project.bookhaven.dto.cartitem.CartItemDto;
import com.project.bookhaven.dto.cartitem.CartItemRequestDto;
import com.project.bookhaven.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    CartItem toModel(CartItemRequestDto requestDto);
}
