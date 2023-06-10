package com.example.effective.mobile.sm.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class AddPostDto {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Schema(description = "Заголовок поста")
    private String title;

    @NotBlank(message = "Title is required")
    @Size(max = 20000, message = "Description cannot exceed 20000 characters")
    @Schema(description = "Описание поста")
    private String description;

    @Schema(description = "Картинка прикрепленная к посту. Разрешены форматы: JPG, PNG, GIF.")
    @JsonProperty("image")
    private MultipartFile image;

}
