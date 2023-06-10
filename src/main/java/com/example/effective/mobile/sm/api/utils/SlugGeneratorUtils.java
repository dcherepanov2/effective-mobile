package com.example.effective.mobile.sm.api.utils;

import java.security.SecureRandom;
import java.util.UUID;


public class SlugGeneratorUtils {

    private static final SecureRandom secureRandom = new SecureRandom();

    private SlugGeneratorUtils(){

    }

    public static String generateSlug(String startString){
        UUID uuid = UUID.randomUUID();
        String slug = startString + "-" + uuid + "-" + secureRandom.nextLong();
        return slug.length() > 255 ? slug.substring(0,255): slug;
    }
}
