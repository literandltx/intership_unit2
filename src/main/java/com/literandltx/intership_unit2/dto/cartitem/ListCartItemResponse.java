package com.literandltx.intership_unit2.dto.cartitem;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data

public class ListCartItemResponse {
    private List<CartItemResponse> list = new ArrayList<>();
    private Integer totalPages;
}
