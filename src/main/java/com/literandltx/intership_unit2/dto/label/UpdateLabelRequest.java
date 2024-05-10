package com.literandltx.intership_unit2.dto.label;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLabelRequest {
    @NotBlank
    private String name;
}
