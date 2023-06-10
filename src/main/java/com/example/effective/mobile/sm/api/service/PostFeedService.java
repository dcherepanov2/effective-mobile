package com.example.effective.mobile.sm.api.service;

import com.example.effective.mobile.sm.api.data.Post;
import com.example.effective.mobile.sm.api.data.User;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostFeedService {
    List<Post> getNews(User user, Pageable pageable);
}
