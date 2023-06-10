package com.example.effective.mobile.sm.api.controllers;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import com.example.effective.mobile.sm.api.security.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class PostsFeedController {

    @Value("${first.user.contact}")
    private String userContact;

    @Value("${posts.follower.count.max}")
    private Integer maxCount;

    private final MockMvc mockMvc;

    private final JwtService jwtService;

    private final UserRepository userRepository;


    @Autowired
    public PostsFeedController(MockMvc mockMvc, JwtService jwtService, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Test
    void getRespondentsPosts() throws Exception {
        User userByContact = userRepository.findUserByContact(userContact);
        String token = "Bearer_" + jwtService.generateToken(userByContact);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts-feed/get-respondents-posts")
                        .header("Authorization", token));
        String jsonResponse = perform.andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode postsNode = jsonNode.get("posts");
        int actualCount = postsNode.size();
        assertEquals(maxCount, actualCount);
        perform.andExpect(MockMvcResultMatchers.content()
                .string(containsString("image")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(containsString("title")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(containsString("description")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(containsString("slug")));
        assertThat(jsonResponse, not(containsString("null")));//проверям, что значения полей в ответе не должны быть null
    }
}
