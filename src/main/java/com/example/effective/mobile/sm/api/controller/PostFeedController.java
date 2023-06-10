package com.example.effective.mobile.sm.api.controller;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.response.PostResponseListDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorResponseDto;
import com.example.effective.mobile.sm.api.dto.response.PostResponseDto;
import com.example.effective.mobile.sm.api.service.PostFeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/posts-feed")
public class PostFeedController {

    @Value("${posts.follower.count.max}")
    private Integer maxPageSize;

    @Qualifier("postFeedServiceImpl")
    private final PostFeedService postFeedService;

    @Autowired
    public PostFeedController(PostFeedService postFeedService) {
        this.postFeedService = postFeedService;
    }

    @Operation(summary = "Получить все посты", description = "Получение списка всех постов пользователей, на которых ты подписан")
    @Parameter(name = "Authorization",description = "Токен авторизации должен начинаться СТРОГО с Bearer_ и содержать полученные подписанные данные из запроса авторизации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(schema = @Schema(implementation = PostResponseListDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "В случае, если пользователь не был авторизован(в header отсутсвует Bearer_{token})",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/get-respondents-posts")
    public PostResponseListDto getNews(@PageableDefault(size = 20, sort = {"createDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                                       @AuthenticationPrincipal User user){
        if (pageable.getPageSize() > maxPageSize) {
            pageable = PageRequest.of(pageable.getPageNumber(), maxPageSize, pageable.getSort());
        }
        List<PostResponseDto> posts = postFeedService.getNews(user, pageable)//todo: дописать еще user к которому он был привязан, мы должны понимать от какого респодента этот пост
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        return new PostResponseListDto(posts);
    }
}
