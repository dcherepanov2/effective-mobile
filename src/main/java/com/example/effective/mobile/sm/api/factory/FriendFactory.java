package com.example.effective.mobile.sm.api.factory;


import com.example.effective.mobile.sm.api.data.Follower;
import com.example.effective.mobile.sm.api.data.Friend;
import com.example.effective.mobile.sm.api.data.User;

import java.util.List;

public interface FriendFactory {
    Friend createFriend(User friend, User friend1, List<Follower> followers);
}
