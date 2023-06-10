package com.example.effective.mobile.sm.api.controller;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.FollowerDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorResponseDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorValidateFieldDto;
import com.example.effective.mobile.sm.api.dto.response.FollowResponseDto;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;
import com.example.effective.mobile.sm.api.exception.DeletePublisherNotFoundException;
import com.example.effective.mobile.sm.api.exception.FollowerCreateException;
import com.example.effective.mobile.sm.api.exception.FollowerDeleteException;
import com.example.effective.mobile.sm.api.exception.PublisherNotFoundException;
import com.example.effective.mobile.sm.api.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/v1/interaction")
@Tag(name = "Взаимодействие пользователей", description = "Контроллер созданный для взамиодействия пользователей между собой")
public class UserInteractionController {

    @Qualifier("followServiceImpl")
    private final FollowService followService;

    @Autowired
    public UserInteractionController(FollowService followService) {
        this.followService = followService;
    }

    @Operation(summary = "Эндпоинт подписки/добавления в друзья пользователя.")
    @Parameter(name = "Authorization",description = "Токен авторизации должен начинаться СТРОГО с Bearer_ и содержать полученные подписанные данные из запроса авторизации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "Если пользователь попробует подписаться сам на себя",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401",
                         description = "В случае, если пользователь не был авторизован(в header отсутсвует Bearer_{token})",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404",
                         description = "Если контакт указанный пользователем для подписики не существует",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "Внутренняя ошибка сервера",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/follow")
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public FollowResponseDto followPublisher(@RequestBody @Valid FollowerDto followerDto, @AuthenticationPrincipal User user) throws PublisherNotFoundException, FollowerCreateException {
        return followService.follow(followerDto, user);
    }

    @Operation(summary = "Эндпоинт удаления друга или подписки.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "Если пользователь попробует подписаться сам на себя",
                         content = @Content(schema = @Schema(implementation = ErrorValidateFieldDto.class))),
            @ApiResponse(responseCode = "401",
                         description = "В случае, если пользователь не был авторизован(в header отсутсвует Bearer_{token})",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404",
                         description = "Если контакт указанный пользователем контакт для удаления не существует",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "Внутренняя ошибка сервера",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/delete-follow-or-friend")
    public ResultDto deleteFriend(@RequestBody @Valid FollowerDto followerDto, @AuthenticationPrincipal User user) throws DeletePublisherNotFoundException, FollowerDeleteException {
        followService.deleteFriend(followerDto,user);
        return new ResultDto(true);
    }
}
