package com.example.effective.mobile.sm.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Имя должно содержать от 3 до 255 символов")
    @Size(min = 3, max = 255, message = "Имя должно содержать от 3 до 255 символов")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Имя должно содержать от 3 до 255 символов")
    @Email(message = "Передан некорректный email, проверьте правильность ввода")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Имя должно содержать от 3 до 255 символов")
    @Size(min = 8, max = 255, message = "Пароль должен быть от 8 до 255 символов")
    @JsonProperty("password")
    private String password;

}