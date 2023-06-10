package com.example.effective.mobile.sm.api.dto.request;

import lombok.Data;


@Data
public abstract class AuthenticateDto {
    private String contact;
    private String password;
}
