package com.literandltx.intership_unit2.dto.cartitem;

import com.literandltx.intership_unit2.dto.group.GroupResponse;
import com.literandltx.intership_unit2.dto.label.LabelResponse;
import lombok.Data;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class CartItemResponse {
    private Long id;
    private String title;
    private String description;
    private Double rank;
    private GroupResponse group;
    private Set<LabelResponse> labels = new LinkedHashSet<>();
}
