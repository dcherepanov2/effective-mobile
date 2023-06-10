package com.example.effective.mobile.sm.api.service;


import com.example.effective.mobile.sm.api.data.Post;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.AddPostDto;
import com.example.effective.mobile.sm.api.dto.request.EditPostDto;
import com.example.effective.mobile.sm.api.exception.PostNotFoundException;
import com.example.effective.mobile.sm.api.exception.SaveFileException;

import java.io.IOException;


public interface PostsService {
    Post save(AddPostDto request, User user) throws SaveFileException, IOException;

    Post edit(EditPostDto request, User user, String slug) throws PostNotFoundException, SaveFileException, IOException;

    void delete(User user, String slug) throws PostNotFoundException, IOException;
}
