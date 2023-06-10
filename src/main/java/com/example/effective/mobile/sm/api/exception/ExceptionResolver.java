package com.example.effective.mobile.sm.api.exception;

import com.example.effective.mobile.sm.api.dto.response.ErrorResponseDto;
import com.example.effective.mobile.sm.api.dto.response.ErrorValidateFieldDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class ExceptionResolver{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorValidateFieldDto handleValidationException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorValidateFieldDto(400, errors);
    }

    @ExceptionHandler({
            VerifyContactException.class,
            RegistrationException.class,
            FollowerDeleteException.class,
            ChatNotFoundException.class,
            PasswordException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodsException(Exception e){
        return new ErrorResponseDto(400,e.getMessage());
    }


    @ExceptionHandler({
            UsernameNotFoundException.class,
            PostNotFoundException.class,
            PublisherNotFoundException.class,
            DeletePublisherNotFoundException.class,
            SaveFileException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFound(Exception e){
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAccessDeniedException(Exception e) {
        return new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleInternalError(Exception e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Произошла внутренняя ошибка сервера");
    }

}
