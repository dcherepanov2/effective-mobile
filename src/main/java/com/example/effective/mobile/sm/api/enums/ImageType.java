package com.example.effective.mobile.sm.api.enums;

import lombok.Getter;


public enum ImageType {
    JPEG("image/jpeg", ".jpeg"),
    PNG("image/png", ".png"),
    GIF("image/gif", ".gif");

    @Getter
    private final String contentType;

    @Getter
    private final String extension;

    ImageType(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }


}
