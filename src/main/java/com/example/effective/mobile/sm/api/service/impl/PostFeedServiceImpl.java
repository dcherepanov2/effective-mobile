package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.Post;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.repo.PostsRepository;
import com.example.effective.mobile.sm.api.service.PostFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("postFeedServiceImpl")
public class PostFeedServiceImpl implements PostFeedService {

    private final PostsRepository postsRepository;

    @Autowired
    public PostFeedServiceImpl(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Override
    public List<Post> getNews(User user, Pageable page) {
        Page<Post> newPosts = postsRepository.getNewPosts(user, page);
        return newPosts.getContent();
    }
}
