package com.literandltx.intership_unit2.dto.cartitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadCartItemRequest {
    @NotBlank
    private String title;

    private String description;

    private Double rank;

    @NotNull
    private Long groupId;

    private String labels;
}
