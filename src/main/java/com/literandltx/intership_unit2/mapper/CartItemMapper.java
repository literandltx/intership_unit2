package com.literandltx.intership_unit2.mapper;

import com.literandltx.intership_unit2.config.MapperConfig;
import com.literandltx.intership_unit2.dto.cartitem.CartItemRequest;
import com.literandltx.intership_unit2.dto.cartitem.CartItemResponse;
import com.literandltx.intership_unit2.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    CartItemResponse toDto(
            final CartItem model
    );

    @Mapping(target = "group.id", ignore = true)
    CartItem toModel(
            final CartItemRequest request
    );
}
