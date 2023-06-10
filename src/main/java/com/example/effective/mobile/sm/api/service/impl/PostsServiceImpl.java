package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.Post;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.AddPostDto;
import com.example.effective.mobile.sm.api.dto.request.EditPostDto;
import com.example.effective.mobile.sm.api.exception.PostNotFoundException;
import com.example.effective.mobile.sm.api.exception.SaveFileException;
import com.example.effective.mobile.sm.api.repo.PostsRepository;
import com.example.effective.mobile.sm.api.service.PostsService;
import com.example.effective.mobile.sm.api.service.ResourceStorage;
import com.example.effective.mobile.sm.api.utils.SlugGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;


@Service("postServiceImpl")
public class PostsServiceImpl implements PostsService {

    @Value("${upload.path.image.posts}")
    private String pathSavePostImage;

    @Qualifier("resourceStorageImpl")
    private final ResourceStorage resourceStorage;

    private final PostsRepository postsRepository;

    @Autowired
    public PostsServiceImpl(ResourceStorage resourceStorage, PostsRepository postsRepository) {
        this.resourceStorage = resourceStorage;
        this.postsRepository = postsRepository;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public Post save(AddPostDto request, User user) throws SaveFileException, IOException {
        Post post = new Post();
        post.setDescription(request.getDescription());
        String imagePath = resourceStorage.saveAndGetPath(pathSavePostImage, request.getImage());
        post.setImage(imagePath);
        post.setSlug(SlugGeneratorUtils.generateSlug("post"));
        post.setTitle(request.getTitle());
        post.setUser(user);
        post.setCreateDate(LocalDateTime.now());
        return postsRepository.save(post);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public Post edit(EditPostDto request, User user, String slug) throws PostNotFoundException, SaveFileException, IOException {
        Post post = postsRepository.findBySlug(slug);
        if(post == null)
            throw new PostNotFoundException("Не найден пост с заданным индетификатором.");
        if(!user.getId().equals(post.getUser().getId())){
            throw new AccessDeniedException("Нельзя изменить пост, который Вам не принадлежит.");
        }
        if(request.getTitle() != null){
            post.setTitle(request.getTitle());
        }
        if(request.getDescription() != null){
            post.setDescription(request.getDescription());
        }
        if(request.getImage() != null){
            String imagePath = resourceStorage.saveAndGetPath(pathSavePostImage, request.getImage());
            post.setImage(imagePath);
        }
        return postsRepository.save(post);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void delete(User user, String slug) throws PostNotFoundException, IOException {
        Post post = postsRepository.findBySlug(slug);
        if(post == null)
            throw new PostNotFoundException("Не найден пост с заданным индетификатором.");
        if(!user.getId().equals(post.getUser().getId())){
            throw new AccessDeniedException("Нельзя удалить пост, который Вам не принадлежит.");
        }
        resourceStorage.deleteFile(post.getImage());
        postsRepository.delete(post);
    }
}
