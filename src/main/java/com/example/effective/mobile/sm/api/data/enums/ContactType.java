package com.example.effective.mobile.sm.api.data.enums;


import java.util.HashSet;
import java.util.Set;


public enum ContactType {
    PHONE(Short.parseShort(String.valueOf(1))),
    EMAIL(Short.parseShort(String.valueOf(2)));

    private final short code;

    public short getCode() {
        return code;
    }

    public Set<ContactType> getByCode(short code){
        ContactType[] contactTypes = ContactType.values();
        Set<ContactType> contactTypeList = new HashSet<>();
        for(ContactType contactType:contactTypes){
            if(contactType.getCode()==code){
                contactTypeList.add(contactType);
            }
        }
        return contactTypeList;
    }

    ContactType(short code) {
        this.code = code;
    }

}