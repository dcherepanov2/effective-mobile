package com.example.effective.mobile.sm.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class ErrorResponseDto {
    @Getter
    @Schema(description = "HTTP код ошибки")
    private final Integer code;

    @Getter
    @Schema(description = "Сообщение содержащие информацию об ошибке")
    private final String message;
}
