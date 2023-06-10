package com.example.effective.mobile.sm.api.exception;

import com.example.effective.mobile.sm.api.dto.response.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component("delegatedEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public DelegatedAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), authException.getLocalizedMessage());
        //if(authException instanceof JwtAuthenticationException){
        //    System.out.println("hello");
        //} todo почему-то он говорит, что это объект не пренадлежит JwtAuthenticationException, хотя по дебагу видно что бросается нужное мне исключени
        // todo почитать новую доку security если останется время
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String resBody = objectMapper.writeValueAsString(errorResponseDto);
        response.getWriter().write(resBody);
    }
}
