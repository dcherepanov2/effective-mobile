package com.example.effective.mobile.sm.api.service;

import com.example.effective.mobile.sm.api.data.UserContact;
import com.example.effective.mobile.sm.api.dto.request.ApproveContactDto;
import com.example.effective.mobile.sm.api.dto.request.ContactDto;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;
import com.example.effective.mobile.sm.api.exception.VerifyContactException;

public interface UserContactService {
    public UserContact save(ContactDto request, Integer code);

    ResultDto verifyCode(ApproveContactDto approveContactDto) throws VerifyContactException;
}
