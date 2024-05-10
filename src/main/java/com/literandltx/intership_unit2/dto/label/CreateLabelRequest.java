package com.literandltx.intership_unit2.dto.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLabelRequest {
    @NotBlank
    private String name;

    @NotNull
    private Long cartItemId;
}
