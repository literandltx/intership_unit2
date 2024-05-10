package com.literandltx.intership_unit2.dto.cartitem;

import lombok.Data;

@Data
public class UploadResponse {
    private Integer success;
    private Integer failed;

    public UploadResponse() { }

    public UploadResponse(
            int success,
            int failed
    ) {
        this.success = success;
        this.failed = failed;
    }
}
