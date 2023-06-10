package com.example.effective.mobile.sm.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.Getter;

public class ContactEmailDto extends ContactDto{

    @Email(message = "Невалидный email")
    @Getter
    private String email;

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
        super.setContact(email);
    }
}
