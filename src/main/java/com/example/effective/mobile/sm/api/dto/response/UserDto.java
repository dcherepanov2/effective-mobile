package com.example.effective.mobile.sm.api.dto.response;

import com.example.effective.mobile.sm.api.data.User;
import lombok.Getter;

import java.time.LocalDateTime;



public class UserDto {
    @Getter
    private String name;
    @Getter
    private LocalDateTime createDate;

    public UserDto(User user) {
        this.name = user.getName();
        this.createDate = user.getCreateDate();
    }
}
