package com.example.effective.mobile.sm.api.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


public class EmailAuthenticationResponse extends AuthenticateDto{
    @Email(message = "Невалидный email")
    @JsonProperty("contact")
    private String contact;

    @Size(min = 8, max = 255, message = "Пароль не может быть меньше 8 и больше 255 символов")
    @JsonProperty("password")
    private String password;

    public EmailAuthenticationResponse(String email, String password) {
        super.setContact(email);
        super.setPassword(password);
        this.contact = email;
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        super.setContact(contact);
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        super.setContact(password);
        this.password = password;
    }
}
