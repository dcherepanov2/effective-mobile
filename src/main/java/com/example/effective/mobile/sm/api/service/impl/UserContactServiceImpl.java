package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.UserContact;
import com.example.effective.mobile.sm.api.data.enums.ContactType;
import com.example.effective.mobile.sm.api.dto.request.ApproveContactDto;
import com.example.effective.mobile.sm.api.dto.request.ContactDto;
import com.example.effective.mobile.sm.api.dto.request.ContactEmailDto;
import com.example.effective.mobile.sm.api.dto.response.ResultDto;
import com.example.effective.mobile.sm.api.exception.VerifyContactException;
import com.example.effective.mobile.sm.api.repo.UserContactRepo;
import com.example.effective.mobile.sm.api.service.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userContactServiceImpl")
public class UserContactServiceImpl implements UserContactService {

    private final UserContactRepo userContactRepo;

    @Autowired
    public UserContactServiceImpl(UserContactRepo userContactRepo) {
        this.userContactRepo = userContactRepo;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public UserContact save(ContactDto request, Integer code) {
        UserContact userContact = new UserContact();
        userContact.setContact(request.getContact());
        userContact.setApproved(false);
        if (request instanceof ContactEmailDto) {
            userContact.setType(ContactType.EMAIL);
        } else {
            userContact.setType(ContactType.PHONE);
        }
        userContact.setUserId(null);
        userContact.setCode(code);
        return userContactRepo.save(userContact);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public ResultDto verifyCode(ApproveContactDto approveContactDto) throws VerifyContactException {
        UserContact verifyContact = userContactRepo.findByContactAndApprovedAndCodeOrderByCodeTimeDesc(approveContactDto.getContact(),false,approveContactDto.getCode());
        if(verifyContact == null)
            throw new VerifyContactException("Неверный код верификации");
        verifyContact.setApproved(true);
        userContactRepo.save(verifyContact);
        return new ResultDto(true);
    }
}
