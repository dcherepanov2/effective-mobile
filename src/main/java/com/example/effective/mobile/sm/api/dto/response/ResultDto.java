package com.example.effective.mobile.sm.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ResultDto {
    public ResultDto(Boolean result) {
        this.result = result;
    }

    @Schema(description = "Результат выполнения запроса")
    private final Boolean result;
}
