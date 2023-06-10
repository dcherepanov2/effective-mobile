package com.example.effective.mobile.sm.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseListDto {

    @Schema(name = "posts", description = "Посты представленые в виде списка")
    @JsonProperty("posts")
    private List<PostResponseDto> responseDtos;
}
