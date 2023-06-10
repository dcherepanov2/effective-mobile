package com.example.effective.mobile.sm.api.factory;

import com.example.effective.mobile.sm.api.data.Follower;
import com.example.effective.mobile.sm.api.data.Friend;
import com.example.effective.mobile.sm.api.data.User;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("friendFactoryImpl")
public class FriendFactoryImpl implements FriendFactory{
    @Override
    public Friend createFriend(User friend, User friend1, List<Follower> followers) {
        if(isFriend(followers,friend1)){
            Friend friendResponse = new Friend();
            Friend.FriendId id = new Friend.FriendId();
            id.setUserId(friend.getId());
            id.setFriendId(friend.getId());
            friendResponse.setId(id);
            friendResponse.setUser(friend);
            friendResponse.setFriend_id(friend1);
            return friendResponse;
        }
        return null;
    }

    private boolean isFriend(List<Follower> followers, User respondent) {
        if (followers != null && !followers.isEmpty()) {
            for (Follower followerObj : followers) {
                if (followerObj.getFollower().getId().equals(respondent.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
