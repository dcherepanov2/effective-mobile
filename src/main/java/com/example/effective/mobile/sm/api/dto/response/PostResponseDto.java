package com.example.effective.mobile.sm.api.dto.response;

import com.example.effective.mobile.sm.api.data.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


public class PostResponseDto {
    @Schema(description = "Заголовок созданного поста")
    @Getter
    private String title;

    @Schema(description = "Мнеманический идентификатор")
    @Getter
    private String slug;

    @Schema(description = "Описание поста")
    @Getter
    private String description;

    @Schema(description = "Путь до картинки. Можно будет по ее пути получить доступ к статическому ресурсу сервера.")
    @Getter
    private String image;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.image = post.getImage();
        this.description = post.getDescription();
        this.slug = post.getSlug();
    }
}
