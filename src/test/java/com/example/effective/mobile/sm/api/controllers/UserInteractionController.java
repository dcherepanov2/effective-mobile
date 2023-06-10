package com.example.effective.mobile.sm.api.controllers;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.dto.request.FollowerDto;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import com.example.effective.mobile.sm.api.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserInteractionController {

    @Value("${third.user.contact}")
    private String thirdUserContact;

    @Value("${fourth.user.contact}")
    private String fourthUserContact;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Autowired
    public UserInteractionController(MockMvc mockMvc, ObjectMapper objectMapper, JwtService jwtService, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Order(1)
    @Test
    void followOnRespondent() throws Exception {
        User user = userRepository.findUserByContact(thirdUserContact);
        user.setContact(thirdUserContact);
        String tokenThirdUser = "Bearer_" + jwtService.generateToken(user);
        FollowerDto followerDto = new FollowerDto();
        followerDto.setPublisherContact(fourthUserContact);
        String requestBody = objectMapper.writeValueAsString(followerDto);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/interaction/follow")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody).header("Authorization",tokenThirdUser));
        perform.andExpect(status().isOk());
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("You have successfully subscribed to a user")));
    }

    @Order(2)
    @Test
    void respondentAddFriendFollower() throws Exception {
        User user = userRepository.findUserByContact(fourthUserContact);
        user.setContact(fourthUserContact);
        String tokenFourthUser = "Bearer_" + jwtService.generateToken(user);
        FollowerDto followerDto = new FollowerDto();
        followerDto.setPublisherContact(thirdUserContact);
        String requestBody = objectMapper.writeValueAsString(followerDto);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/interaction/follow")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody).header("Authorization",tokenFourthUser));
        perform.andExpect(status().isOk());
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("The user has been successfully added as a friend")));
    }


    @Order(3)
    @Test
    void getChat() throws Exception {
        User user = userRepository.findUserByContact(fourthUserContact);
        user.setContact(fourthUserContact);
        String tokenFourthUser = "Bearer_" + jwtService.generateToken(user);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/interaction/get-chat-by-contact/"+thirdUserContact)
                .contentType(MediaType.APPLICATION_JSON).header("Authorization",tokenFourthUser));
        perform.andExpect(status().isOk()).andDo(print());
    }

    @Order(4)
    @Test
    void deleteFriend() throws Exception {
        User user = userRepository.findUserByContact(fourthUserContact);
        user.setContact(fourthUserContact);
        String tokenFourthUser = "Bearer_" + jwtService.generateToken(user);
        FollowerDto followerDto = new FollowerDto();
        followerDto.setPublisherContact(thirdUserContact);
        String requestBody = objectMapper.writeValueAsString(followerDto);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/interaction/delete-follow-or-friend")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody).header("Authorization",tokenFourthUser));
        perform.andExpect(status().isOk());
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("result")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("true")));
    }

    @Order(4)
    @Test
    void deleteFollower() throws Exception {
        User user = userRepository.findUserByContact(thirdUserContact);
        user.setContact(thirdUserContact);
        String tokenFourthUser = "Bearer_" + jwtService.generateToken(user);
        FollowerDto followerDto = new FollowerDto();
        followerDto.setPublisherContact(fourthUserContact);
        String requestBody = objectMapper.writeValueAsString(followerDto);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/interaction/delete-follow-or-friend")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody).header("Authorization",tokenFourthUser));
        perform.andExpect(status().isOk());
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("result")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("true")));
    }
}
