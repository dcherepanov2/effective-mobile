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
//        if(!(authException instanceof BadCredentialsException) && !(authException instanceof UsernameNotFoundException)){
//            errorResponseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), "Your token is not valid");// todo удалить этот костыль если останется время, могут быть проблемы с масштабируемостью
//        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String resBody = objectMapper.writeValueAsString(errorResponseDto);
        response.getWriter().write(resBody);
    }
}
