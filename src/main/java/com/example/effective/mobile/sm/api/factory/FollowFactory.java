package com.example.effective.mobile.sm.api.factory;


import com.example.effective.mobile.sm.api.data.Follower;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.exception.FollowerCreateException;

public interface FollowFactory {
    Follower createFollower(User respondent, User follower) throws FollowerCreateException;
}
