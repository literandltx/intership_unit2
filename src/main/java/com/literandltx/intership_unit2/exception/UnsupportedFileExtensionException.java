package com.literandltx.intership_unit2.exception;

public class UnsupportedFileExtensionException extends RuntimeException {
    public UnsupportedFileExtensionException(
            final String message
    ) {
        super(message);
    }

    public UnsupportedFileExtensionException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}
