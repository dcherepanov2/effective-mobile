package com.example.effective.mobile.sm.api.data.converts;


import com.example.effective.mobile.sm.api.data.enums.ContactType;
import jakarta.persistence.AttributeConverter;

public class ContactTypeConverter implements AttributeConverter<ContactType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ContactType contactType) {
        return switch (contactType) {
            case EMAIL -> 1;
            case PHONE -> 2;
        };
    }

    @Override
    public ContactType convertToEntityAttribute(Integer integer) {
        return switch (integer) {
            case 1 -> ContactType.EMAIL;
            case 2 -> ContactType.PHONE;
            default -> null;
        };
    }
}
