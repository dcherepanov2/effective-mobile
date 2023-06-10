package com.example.effective.mobile.sm.api.controller;

import com.example.effective.mobile.sm.api.dto.request.ApproveEmailDto;
import com.example.effective.mobile.sm.api.dto.request.ContactEmailDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorResponseDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorValidateFieldDto;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;
import com.example.effective.mobile.sm.api.exception.VerifyContactException;
import com.example.effective.mobile.sm.api.service.NotificationService;
import com.example.effective.mobile.sm.api.service.UserContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user-contact")
@Validated
@Tag(name = "Рассылка сообщений с подтверждающим кодом", description = "Контроллер созданный для авторизации и регистрации пользователя")
public class UserContactController {

    @Qualifier("notificationServiceImpl")
    private final NotificationService notificationService;

    @Qualifier("userContactServiceImpl")
    private final UserContactService userContactService;

    @Autowired
    public UserContactController(NotificationService notificationService, UserContactService userContactService) {
        this.notificationService = notificationService;
        this.userContactService = userContactService;
    }

    @Operation(summary = "Эндпоинт для отправки кода подтверждения", description = "Нужен для рассылки кода подтверждения контакта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "В запросе указаны невалдиные данные",
                         content = @Content(schema = @Schema(implementation = ErrorValidateFieldDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "Внутренняя ошибка сервера",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/send-approve-code")
    public ResultDto sendApproveCode(@RequestBody @Valid ContactEmailDto contactDto){
        return notificationService.sendEmailCodeMessage(contactDto);
    }

    @Operation(summary = "Эндпоинт для подтверждения контакта", description = "Нужен для того, чтобы пользователь подтвердил свои контакты до регистрации.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "400",
                         description = "Пользователя с указанным контактом не найдено",
                         content = @Content(schema = @Schema(implementation = ErrorValidateFieldDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "Внутренняя ошибка сервера",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/approve-code")
    public ResultDto verifyApproveCode(@RequestBody @Valid ApproveEmailDto approveContactDto) throws VerifyContactException {
        return userContactService.verifyCode(approveContactDto);
    }
}
