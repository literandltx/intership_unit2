package com.literandltx.intership_unit2.dto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupRequest {
    @NotBlank
    private String title;
}
