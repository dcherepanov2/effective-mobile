package com.example.effective.mobile.sm.api.factory;

import com.example.effective.mobile.sm.api.data.Follower;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.exception.FollowerCreateException;
import org.springframework.stereotype.Component;



@Component("followFactoryImpl")
public class FollowFactoryImpl implements FollowFactory{
    @Override
    public Follower createFollower(User respondent, User follower) throws FollowerCreateException {
        if(respondent.getId().equals(follower.getId())){
            throw new FollowerCreateException("Нельзя подписаться на самого себя.");
        }
        Follower followerObject = new Follower();
        Follower.FollowerId followerId = new Follower.FollowerId();
        followerId.setFollowerId(follower.getId());
        followerId.setUserId(respondent.getId());
        followerObject.setId(followerId);
        followerObject.setUser(respondent);
        followerObject.setFollower(follower);
        return followerObject;
    }
}
