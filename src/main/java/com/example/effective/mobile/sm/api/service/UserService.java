package com.example.effective.mobile.sm.api.service;


import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.AuthenticateDto;
import com.example.effective.mobile.sm.api.dto.request.RegisterDto;
import com.example.effective.mobile.sm.api.dto.response.AuthenticationResponse;
import com.example.effective.mobile.sm.api.exception.PasswordException;
import com.example.effective.mobile.sm.api.exception.RegistrationException;
import com.example.effective.mobile.sm.api.exception.UsernameNotFoundException;

public interface UserService {

    User register(RegisterDto request) throws RegistrationException;

    AuthenticationResponse authenticate(AuthenticateDto authenticationDto) throws PasswordException, UsernameNotFoundException;
}
