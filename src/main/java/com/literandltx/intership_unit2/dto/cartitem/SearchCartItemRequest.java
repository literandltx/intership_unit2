package com.literandltx.intership_unit2.dto.cartitem;

import com.literandltx.intership_unit2.dto.cartitem.search.NumberRangeRequest;
import lombok.Data;
import java.util.List;

@Data
public class SearchCartItemRequest {
    private List<String> titles;
    private List<String> description;
    private NumberRangeRequest rank;
    private List<Long> groupIds;
}
