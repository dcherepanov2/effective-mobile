package com.example.effective.mobile.sm.api.service;


import com.example.effective.mobile.sm.api.data.Chat;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.FollowerDto;
import com.example.effective.mobile.sm.api.dto.response.FollowResponseDto;
import com.example.effective.mobile.sm.api.exception.*;

public interface FollowService {

    FollowResponseDto follow(FollowerDto request, User user) throws PublisherNotFoundException, FollowerCreateException;

    void deleteFriend(FollowerDto followerDto, User user) throws DeletePublisherNotFoundException, FollowerDeleteException;

    Chat getChat(User user, String contact) throws ChatNotFoundException;
}
