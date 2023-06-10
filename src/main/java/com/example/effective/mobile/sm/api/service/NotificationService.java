package com.example.effective.mobile.sm.api.service;


import com.example.effective.mobile.sm.api.dto.request.ContactDto;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;

public interface NotificationService {

    ResultDto sendEmailCodeMessage(ContactDto contact);
}
