package com.example.effective.mobile.sm.api.utils;

import java.security.SecureRandom;


public class UserContactUtils {
    private static final SecureRandom random = new SecureRandom();
    private UserContactUtils(){

    }
    public static Integer generateCode(){
        int min = 100_000;
        int max = 999_999;
        return random.nextInt(max - min + 1) + min;
    }
}
