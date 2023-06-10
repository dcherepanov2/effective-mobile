package com.example.effective.mobile.sm.api.repo;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.data.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserContactRepo extends JpaRepository<UserContact, Long> {
    UserContact findByContactAndApprovedAndCodeOrderByCodeTimeDesc(String contact, Boolean approved, Integer code);

    UserContact findByContactOrderByCodeTime(String contact);

    List<UserContact> findAllByUserId(User id);
}
