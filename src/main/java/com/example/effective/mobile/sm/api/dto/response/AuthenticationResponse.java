package com.example.effective.mobile.sm.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class AuthenticationResponse {

    @Getter
    @Setter
    @JsonProperty("token")
    @Schema(description = "Jwt токен")
    private String token;
}
