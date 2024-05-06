package com.literandltx.intership_unit2.dto.cartitem;

import com.literandltx.intership_unit2.dto.group.GroupResponse;
import lombok.Data;

@Data
public class CartItemResponse {
    private Long id;
    private String title;
    private String description;
    private Double rank;
    private GroupResponse group;
}
