package com.example.effective.mobile.sm.api.exception;



public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);

    }
}
