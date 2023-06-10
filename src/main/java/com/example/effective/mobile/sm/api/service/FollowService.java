package com.example.effective.mobile.sm.api.service;


import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.FollowerDto;
import com.example.effective.mobile.sm.api.dto.response.FollowResponseDto;
import com.example.effective.mobile.sm.api.exception.DeletePublisherNotFoundException;
import com.example.effective.mobile.sm.api.exception.FollowerCreateException;
import com.example.effective.mobile.sm.api.exception.FollowerDeleteException;
import com.example.effective.mobile.sm.api.exception.PublisherNotFoundException;

public interface FollowService {

    FollowResponseDto follow(FollowerDto request, User user) throws PublisherNotFoundException, FollowerCreateException;

    void deleteFriend(FollowerDto followerDto, User user) throws DeletePublisherNotFoundException, FollowerDeleteException;
}
