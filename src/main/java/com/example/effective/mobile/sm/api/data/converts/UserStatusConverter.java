package com.example.effective.mobile.sm.api.data.converts;


import com.example.effective.mobile.sm.api.data.enums.USER_STATUS;
import jakarta.persistence.AttributeConverter;

public class UserStatusConverter implements AttributeConverter<USER_STATUS, Integer> {
    @Override
    public Integer convertToDatabaseColumn(USER_STATUS userStatus) {
        return switch (userStatus) {
            case ENABLED -> 1;
            case DISABLED -> 2;
        };
    }

    @Override
    public USER_STATUS convertToEntityAttribute(Integer number) {
        return switch (number) {
            case 1 -> USER_STATUS.ENABLED;
            case 2 -> USER_STATUS.DISABLED;
            default -> null;
        };
    }
}
