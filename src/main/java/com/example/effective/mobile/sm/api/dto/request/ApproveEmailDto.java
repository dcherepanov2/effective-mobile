package com.example.effective.mobile.sm.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;


public class ApproveEmailDto extends ApproveContactDto{

    @Email(message = "Невалидный email.")
    @Getter
    private String email;

    @Getter
    @Min(value = 100000, message = "Код должен быть от 100000 до 999999")
    @Max(value = 999999, message = "Код должен быть от 100000 до 999999")
    private Integer code;

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
        super.setContact(email);
    }

    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
        super.setCode(code);
    }
}
