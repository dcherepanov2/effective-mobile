package com.example.effective.mobile.sm.api.security;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);//todo должен подсоединяться userContact
        if(user == null)
            throw new UsernameNotFoundException("Пользователь с таким email не найден");
        return user;
    }

}

