package com.example.effective.mobile.sm.api.controller;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.EmailAuthenticationResponse;
import com.example.effective.mobile.sm.api.dto.request.RegisterDto;
import com.example.effective.mobile.sm.api.dto.response.AuthenticationResponse;
import com.example.effective.mobile.sm.api.dto.response.ErrorResponseDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorValidateFieldDto;
import com.example.effective.mobile.sm.api.exception.PasswordException;
import com.example.effective.mobile.sm.api.exception.RegistrationException;
import com.example.effective.mobile.sm.api.exception.UsernameNotFoundException;
import com.example.effective.mobile.sm.api.security.JwtService;
import com.example.effective.mobile.sm.api.service.UserService;
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
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "Регистрации и авторизации пользователя", description = "Контроллер для авторизации и регистрации пользователя")
public class AuthenticationController {

    @Qualifier("userServiceImpl")
    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    public AuthenticationController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Эндпоинт для регистриации пользователя", description = "Эндпоинт для регистриации пользователя." +
                                                                                 " До регистрации пользователя нужно обратиться по эндпоинту /user-contact/send-approve-code и " +
                                                                                 " подтвердить контакты, после чего можно зарегистрироваться в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400",
                         description = "В запросе указаны невалдиные данные",
                         content = @Content(schema = @Schema(implementation = ErrorValidateFieldDto.class))),
            @ApiResponse(responseCode = "401",
                         description = "Пользователь не авторизован в системе",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegisterDto registerDto) throws RegistrationException {
        User register = userService.register(registerDto);
        String jwtToken = jwtService.generateToken(register);
        return new AuthenticationResponse(jwtToken);
    }

    @Operation(summary = "Эндпоинт авторизации пользователя", description = "Эндпоинт для авторизации пользователя." +
            " До регистрации пользователя нужно обратиться по ендпоинту /users/registration и " +
            " зарегистрироваться, после чего можно зарегистрироваться в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Запрос успешно выполнен",
                         content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400",
                         description = "Неверный пароль или email",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404",
                         description = "Пользователь с указанными контактами не найден",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/auth")
    public AuthenticationResponse authentication(@RequestBody @Valid EmailAuthenticationResponse authenticationDto) throws PasswordException, UsernameNotFoundException {
        return userService.authenticate(authenticationDto);
    }
}
