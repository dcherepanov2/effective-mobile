package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.UserContact;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;
import com.example.effective.mobile.sm.api.service.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service("emailSender")
public class EmailSender implements NotificationSender<ResultDto, UserContact> {

    @Value("${app.mail}")
    private String email;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public ResultDto sendCode(UserContact contact, Integer code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(contact.getContact());
        simpleMailMessage.setSubject("Verification");
        simpleMailMessage.setText("Input this code to verification on site: " + code);
        javaMailSender.send(simpleMailMessage);
        return null;
    }
}
