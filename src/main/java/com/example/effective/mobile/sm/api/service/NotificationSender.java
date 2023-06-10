package com.example.effective.mobile.sm.api.service;


import com.example.effective.mobile.sm.api.data.UserContact;

public interface NotificationSender<T,D extends UserContact> {
    public T sendCode(D contact, Integer code);
}
