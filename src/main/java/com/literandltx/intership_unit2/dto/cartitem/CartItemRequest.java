package com.literandltx.intership_unit2.dto.cartitem;

import lombok.Data;

@Data
public class CartItemRequest {
    private String title;
    private String description;
    private Double rank;
    private Long groupId;
}
