package com.example.effective.mobile.sm.api.controller;

import com.example.effective.mobile.sm.api.data.Post;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.AddPostDto;
import com.example.effective.mobile.sm.api.dto.request.EditPostDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorResponseDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorValidateFieldDto;
import com.example.effective.mobile.sm.api.dto.response.PostResponseDto;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;
import com.example.effective.mobile.sm.api.exception.PostNotFoundException;
import com.example.effective.mobile.sm.api.exception.SaveFileException;
import com.example.effective.mobile.sm.api.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/posts")
@Validated
@Tag(name = "Создание постов")
public class PostsController {

    @Qualifier("postServiceImpl")
    private final PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @Operation(summary = "Эндпоинт добавления поста.")
    @Parameter(name = "Authorization",description = "Токен авторизации должен начинаться СТРОГО с Bearer_ и содержать полученные подписанные данные из запроса авторизации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         headers = @Header(name = "Authorization", description = "Обязательно должен начинаться с Bearer_ и содержать jwt token"),
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "В запросе указаны невалдиные данные или картинки неразрешенного формата.",
                         content = @Content(schema = @Schema(implementation = ErrorValidateFieldDto.class))),
            @ApiResponse(responseCode = "401",
                         description = "В случае, если пользователь не был авторизован(в header отсутсвует Bearer_{token})",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostResponseDto addPost(@Valid AddPostDto request, @AuthenticationPrincipal User user, HttpServletRequest httpServletRequest) throws SaveFileException, IOException {
        Post save = postsService.save(request, user);
        return new PostResponseDto(save);
    }

    @Operation(summary = "Эндпоинт изменения поста")
    @Parameter(name = "Authorization",description = "Токен авторизации должен начинаться СТРОГО с Bearer_ и содержать полученные подписанные данные из запроса авторизации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "В запросе указаны невалдиные данные или была передана картинка неразрешенного формата",
                         content = @Content(schema = @Schema(implementation = ErrorValidateFieldDto.class))),
            @ApiResponse(responseCode = "401",
                         description = "В случае, если пользователь не был авторизован(в header отсутсвует Bearer_{token})",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403",
                         description = "Нельзя изменить пост в текущей сессии, т.к. он был создан другим пользователем",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404",
                         description = "Пост не был найден по указанному мнеманическом идентификатору",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(value = "/edit/{slug}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostResponseDto editPost(@Valid EditPostDto editPost,
                                    @AuthenticationPrincipal User user,
                                    @Parameter(in = ParameterIn.PATH, name = "slug", required = true, description = "Идентификатор поста, возвращается при его создании в ответе")
                                    @PathVariable("slug") String slug) throws PostNotFoundException, SaveFileException, IOException
    {
        Post edit = postsService.edit(editPost, user, slug);
        return new PostResponseDto(edit);
    }

    @Operation(summary = "Эндпоинт изменения поста.")
    @Parameter(name = "Authorization",description = "Токен авторизации должен начинаться СТРОГО с Bearer_ и содержать полученные подписанные данные из запроса авторизации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "В запросе указаны невалдиные данные",
                         content = @Content(schema = @Schema(implementation = ErrorValidateFieldDto.class))),
            @ApiResponse(responseCode = "401",
                         description = "В случае, если пользователь не был авторизован(в header отсутсвует Bearer_{token})",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403",
                         description = "Нельзя удалить пост в текущей сессии, т.к. он был создан другим пользователем",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404",
                         description = "Пост не был найден по указанному мнеманическом идентификатору",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(value = "/delete/{slug}")
    public ResultDto deletePost(@AuthenticationPrincipal User user,
                                @Parameter(in = ParameterIn.PATH, name = "slug", required = true, description = "Идентификатор поста, возвращается при его создании в ответе")
                                @PathVariable("slug") String slug) throws PostNotFoundException, IOException {
        postsService.delete(user, slug);
        return new ResultDto(true);
    }
}
