package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.data.UserContact;
import com.example.effective.mobile.sm.api.data.enums.USER_STATUS;
import com.example.effective.mobile.sm.api.dto.request.AuthenticateDto;
import com.example.effective.mobile.sm.api.dto.request.RegisterDto;
import com.example.effective.mobile.sm.api.dto.response.AuthenticationResponse;
import com.example.effective.mobile.sm.api.exception.PasswordException;
import com.example.effective.mobile.sm.api.exception.RegistrationException;
import com.example.effective.mobile.sm.api.exception.UsernameNotFoundException;
import com.example.effective.mobile.sm.api.repo.RoleRepository;
import com.example.effective.mobile.sm.api.repo.UserContactRepo;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import com.example.effective.mobile.sm.api.security.JwtService;
import com.example.effective.mobile.sm.api.service.UserService;
import com.example.effective.mobile.sm.api.utils.SlugGeneratorUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;


@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserContactRepo userContactRepo;

    private final RoleRepository roleRepository;

    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserContactRepo userContactRepo, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userContactRepo = userContactRepo;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public User register(RegisterDto request) throws RegistrationException {
        UserContact byContact = userContactRepo.findByContactOrderByCodeTime(request.getEmail());//todo дописать что если контакт иметь пользователя, то его нельзя зарегистрировать
        if (byContact == null) {
            throw new RegistrationException("Нельзя зарегистрировать аккаунт на неподтвержденные контакты.");
        }
        User user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .slug(SlugGeneratorUtils.generateSlug("user"))
                .status(USER_STATUS.ENABLED)
                .createDate(LocalDateTime.now())
                .roles(Collections.singletonList(roleRepository.findByName("ROLE_USER")))
                .build();
        User save = userRepository.save(user);
        byContact.setUserId(user);
        userContactRepo.save(byContact);
        return save;
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticateDto request) throws PasswordException, UsernameNotFoundException {
        User user = userRepository.findUserByContact(request.getContact());
        if (user == null) {
            throw new UsernameNotFoundException("Пользователя с указанным контактом не найдено");
        }
        String hashedPassword = user.getPassword();
        String plainPassword = request.getPassword();
        if (!passwordEncoder.matches(plainPassword, hashedPassword)) {
            throw new PasswordException("Неверный пароль");
        }
        return new AuthenticationResponse(jwtService.generateToken(user));
    }
}
