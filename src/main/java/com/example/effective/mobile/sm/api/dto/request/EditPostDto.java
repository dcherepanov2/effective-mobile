package com.example.effective.mobile.sm.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class EditPostDto {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Title is required")
    @Size(max = 20000, message = "Description cannot exceed 20000 characters")
    private String description;

    private MultipartFile image;

    public EditPostDto() {
    }

    public EditPostDto(String title, String description, MultipartFile image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }
}
