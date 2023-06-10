package com.example.effective.mobile.sm.api.controllers;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.AddPostDto;
import com.example.effective.mobile.sm.api.enums.ImageType;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import com.example.effective.mobile.sm.api.security.JwtService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.file.Files;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostsController {

    @Value("${second.user.contact}")
    private String fourthUserContact;

    private final MockMvc mockMvc;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final String slugPost = "post-slug18";

    @Autowired
    public PostsController(MockMvc mockMvc, JwtService jwtService, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Order(1)
    @Test
    void addPost() throws Exception {
        User user = userRepository.findUserByContact(fourthUserContact);
        String token = "Bearer_" + jwtService.generateToken(user);
        File fileTest = new File("src/test/resources/upload/image/posts/test.png");
        AddPostDto addPostDto = new AddPostDto();
        addPostDto.setTitle("title");
        addPostDto.setDescription("description");
        MockMultipartFile file = new MockMultipartFile("image", "test.png",
                ImageType.PNG.getContentType(), Files.readAllBytes(fileTest.toPath()));

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/posts/add")
                .file(file)
                .param("title", addPostDto.getTitle())
                .param("description", addPostDto.getDescription())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE + ";charset=UTF-8")
                .header("Authorization", token));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("title")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("description")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("test")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("test")));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Order(2)
    @Test
    void editPost() throws Exception {
        User user = userRepository.findUserByContact(fourthUserContact);
        String token = "Bearer_" + jwtService.generateToken(user);
        File fileTest = new File("src/test/resources/upload/image/posts/ship.png");
        AddPostDto addPostDto = new AddPostDto();
        addPostDto.setTitle("title1");
        addPostDto.setDescription("description1");
        MockMultipartFile file = new MockMultipartFile("image", "ship.png",
                ImageType.PNG.getContentType(), Files.readAllBytes(fileTest.toPath()));
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/posts/edit/" + slugPost)
                .file(file)
                .param("title", addPostDto.getTitle())
                .param("description", addPostDto.getDescription())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE + ";charset=UTF-8")
                .header("Authorization", token));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("title1")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("description1")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("ship")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("slug")));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Order(3)
    @Test
    void deletePost() throws Exception {
        User user = userRepository.findUserByContact(fourthUserContact);
        String token = "Bearer_" + jwtService.generateToken(user);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/delete/" + slugPost)
                .header("Authorization", token));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("true")));
    }

}
