package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.UserContact;
import com.example.effective.mobile.sm.api.dto.request.ContactDto;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;
import com.example.effective.mobile.sm.api.service.NotificationSender;
import com.example.effective.mobile.sm.api.service.NotificationService;
import com.example.effective.mobile.sm.api.service.UserContactService;
import com.example.effective.mobile.sm.api.utils.UserContactUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("notificationServiceImpl")
public class NotificationServiceImpl implements NotificationService {

    @Qualifier("emailSender")
    private final NotificationSender<ResultDto, UserContact> emailSender;

    private final UserContactService userContactService;

    @Autowired
    public NotificationServiceImpl(NotificationSender<ResultDto, UserContact> emailSender, UserContactService userContactService) {
        this.emailSender = emailSender;
        this.userContactService = userContactService;
    }

    @Override
    @Transactional
    public ResultDto sendEmailCodeMessage(ContactDto contact) {
        Integer code = UserContactUtils.generateCode();
        UserContact userContact = userContactService.save(contact, code);
        emailSender.sendCode(userContact,code);
        return new ResultDto(true);
    }
}
