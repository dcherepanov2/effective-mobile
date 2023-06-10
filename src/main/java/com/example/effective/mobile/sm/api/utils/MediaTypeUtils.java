package com.example.effective.mobile.sm.api.utils;

import com.example.effective.mobile.sm.api.enums.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;


public class MediaTypeUtils {
    private MediaTypeUtils(){

    }

    public static ImageType getImageType(MultipartFile multipartFile){
        String contentType = multipartFile.getContentType();
        return Arrays.stream(ImageType.values())
                .filter(x -> x.getContentType().equals(contentType))
                .findFirst().orElse(null);
    }
}
