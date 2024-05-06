package com.literandltx.intership_unit2.dto.cartitem;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UploadResponse {
    private Integer success;
    private Integer failed;

    public UploadResponse(
            int success,
            int failed
    ) {
        this.success = success;
        this.failed = failed;
    }
}
