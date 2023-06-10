package com.example.effective.mobile.sm.api.data.enums;

import lombok.Getter;


public enum USER_STATUS {
    ENABLED(1), DISABLED(2);

    @Getter
    private final Integer number;

    USER_STATUS(Integer number) {
        this.number = number;
    }
}
