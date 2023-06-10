package com.example.effective.mobile.sm.api.data.enums;

import lombok.Getter;


public enum FollowerType {
    FOLLOW(1), FRIEND(2);

    @Getter
    private final Integer number;

    FollowerType(Integer number) {
        this.number = number;
    }
}
