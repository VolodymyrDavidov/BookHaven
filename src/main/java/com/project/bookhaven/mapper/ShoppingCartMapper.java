package com.project.bookhaven.mapper;

import com.project.bookhaven.config.MapperConfig;
import com.project.bookhaven.dto.shoppingcart.ShoppingCartDto;
import com.project.bookhaven.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItems", source = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
