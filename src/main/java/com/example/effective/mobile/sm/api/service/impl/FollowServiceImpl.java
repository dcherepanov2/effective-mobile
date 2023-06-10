package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.Chat;
import com.example.effective.mobile.sm.api.data.Follower;
import com.example.effective.mobile.sm.api.data.Friend;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.FollowerDto;
import com.example.effective.mobile.sm.api.dto.response.FollowResponseDto;
import com.example.effective.mobile.sm.api.exception.*;
import com.example.effective.mobile.sm.api.factory.FollowFactory;
import com.example.effective.mobile.sm.api.factory.FriendFactory;
import com.example.effective.mobile.sm.api.repo.FollowRepository;
import com.example.effective.mobile.sm.api.repo.FriendRepository;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import com.example.effective.mobile.sm.api.service.ChatService;
import com.example.effective.mobile.sm.api.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
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

    @Qualifier("chatServiceImpl")
    private final ChatService chatService;

    private final UserRepository userRepository;

    private final FriendRepository friendRepository;
    @Autowired
    public FollowServiceImpl(FollowRepository followRepository, FollowFactory factory, FriendFactory friendFactory, ChatService chatService, UserRepository userRepository, FriendRepository friendRepository) {
        this.followRepository = followRepository;
        this.followFactory = factory;
        this.friendFactory = friendFactory;
        this.chatService = chatService;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
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
        Follower follower = followRepository.findByFollowerAndUser(user,userByContact);
        Friend friend = friendRepository.findByFriendIdOrUserId(user,userByContact);
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

    @Override
    public Chat getChat(User user, String contact) throws ChatNotFoundException {
        User user2 = userRepository.findUserByContact(contact);
        if(user2 == null){
            throw new ChatNotFoundException("The user with the specified contact does not exist in the system.");
        }
        Friend friend = friendRepository.findByFriendIdOrUserId(user,user2);
        if(friend == null){
            throw new AccessDeniedException("You didn't add him as a friend, or he didn't accept your request. The chat functionality with the user is not available.");
        }
        return friend.getChat();
    }

    private Friend saveFriends(User friend, User friend1){
        List<Follower> followers = followRepository.findAllByUser(friend);
        Friend friendCheck = friendRepository.findByFriendIdOrUserId(friend,friend1);
        if(friendCheck == null){
            Friend friendResponse = friendFactory.createFriend(friend,friend1, followers);
            if(friendResponse != null){
                Chat chat = chatService.createChat();
                Friend.FriendId friendId = new Friend.FriendId();
                friendId.setChatId(chat.getChatId());
                friendResponse.setChat(chat);
                if(friendResponse != null){
                    friendRepository.save(friendResponse);
                    return friendResponse;
                }
            }
        }
        return null;
    }
}
