package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.Follower;
import com.example.effective.mobile.sm.api.data.Friend;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.FollowerDto;
import com.example.effective.mobile.sm.api.dto.response.FollowResponseDto;
import com.example.effective.mobile.sm.api.exception.DeletePublisherNotFoundException;
import com.example.effective.mobile.sm.api.exception.FollowerCreateException;
import com.example.effective.mobile.sm.api.exception.FollowerDeleteException;
import com.example.effective.mobile.sm.api.exception.PublisherNotFoundException;
import com.example.effective.mobile.sm.api.factory.FollowFactory;
import com.example.effective.mobile.sm.api.factory.FriendFactory;
import com.example.effective.mobile.sm.api.repo.FollowRepository;
import com.example.effective.mobile.sm.api.repo.FriendRepository;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import com.example.effective.mobile.sm.api.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("followServiceImpl")
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Qualifier("followFactoryImpl")
    private final FollowFactory followFactory;

    @Qualifier("friendFactoryImpl")
    private final FriendFactory friendFactory;

    private final UserRepository userRepository;

    private final FriendRepository friendRepository;
    @Autowired
    public FollowServiceImpl(FollowRepository followRepository, FollowFactory factory, FriendFactory friendFactory, UserRepository userRepository,FriendRepository friendRepository) {
        this.followRepository = followRepository;
        this.followFactory = factory;
        this.friendFactory = friendFactory;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    @Override
    public FollowResponseDto follow(FollowerDto request, User user) throws PublisherNotFoundException, FollowerCreateException {
        User userByContact = userRepository.findUserByContact(request.getPublisherContact());
        if(userByContact == null){
            throw new PublisherNotFoundException("The user with the specified contact does not exist in the system.");
        }
        Follower follower = followFactory.createFollower(userByContact, user);
        Friend friend = saveFriends(user, userByContact);
        followRepository.save(follower);
        FollowResponseDto followResponseDto = new FollowResponseDto();
        followResponseDto.setResult(true);
        if(friend != null)
            followResponseDto.setMessage("The user has been successfully added as a friend");
        else
            followResponseDto.setMessage("You have successfully subscribed to a user");
        return followResponseDto;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void deleteFriend(FollowerDto request, User user) throws DeletePublisherNotFoundException, FollowerDeleteException {
        User userByContact = userRepository.findUserByContact(request.getPublisherContact());
        if(userByContact == null){
            throw new DeletePublisherNotFoundException("Пользователя с указанным контактом не существует в системе.");
        }
        Pageable pageable = PageRequest.of(0, 1);
        Follower follower = followRepository.findByFollowerAndUser(user,userByContact);
        Friend friend = friendRepository.findByFriendIdOrUserId(user,userByContact, pageable).get()
                                                                                             .findFirst()
                                                                                             .orElse(null);
        if(follower == null && friend == null){
            throw new FollowerDeleteException("Перед тем как удалить пользователя из респодентов нужно подписаться на него.");
        }
        if(follower != null){
            followRepository.delete(follower);
        }
        if(friend != null){
            friendRepository.delete(friend);
        }
    }

    private Friend saveFriends(User friend, User friend1){
        Pageable pageable = PageRequest.of(0,1);
        List<Follower> followers = followRepository.findAllByUser(friend);//todo придумать как убрать, вынужденная мера
        Friend friendCheck = friendRepository.findByFriendIdOrUserId(friend,friend1,pageable).stream().findFirst().orElse(null);
        if(friendCheck == null){
            Friend friendResponse = friendFactory.createFriend(friend,friend1, followers);
            if(friendResponse != null){
                friendRepository.save(friendResponse);
                return friendResponse;
            }
        }
        return null;
    }
}
