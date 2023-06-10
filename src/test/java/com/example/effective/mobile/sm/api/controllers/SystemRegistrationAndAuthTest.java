package com.example.effective.mobile.sm.api.controllers;

import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.data.UserContact;
import com.example.effective.mobile.sm.api.dto.request.ApproveEmailDto;
import com.example.effective.mobile.sm.api.dto.request.ContactEmailDto;
import com.example.effective.mobile.sm.api.dto.request.EmailAuthenticationResponse;
import com.example.effective.mobile.sm.api.dto.request.RegisterDto;
import com.example.effective.mobile.sm.api.repo.UserContactRepo;
import com.example.effective.mobile.sm.api.repo.UserRepository;
import com.example.effective.mobile.sm.api.security.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SystemRegistrationAndAuthTest {//решил сделать системный тест, чтобы не создавать и не удалять лишний раз объекты из бд
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserContactRepo userContactRepo;
    private final String email = "danilcherep7@gmail.com";

    private final String password = "123456789";
    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Autowired
    public SystemRegistrationAndAuthTest(MockMvc mockMvc, ObjectMapper objectMapper, UserContactRepo userContactRepo, JwtService jwtService, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userContactRepo = userContactRepo;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Order(1)
    @Test
    void sendApproveCode() throws Exception {
        ContactEmailDto contactEmailDto = new ContactEmailDto();
        contactEmailDto.setEmail(email);
        String requestBody = objectMapper.writeValueAsString(contactEmailDto);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user-contact/send-approve-code")
                                      .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("result")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("true")));
    }

    @Order(2)
    @Test
    void approveCode() throws Exception {
        UserContact userContact = userContactRepo.findByContactOrderByCodeTime(email);
        ApproveEmailDto approveEmailDto = new ApproveEmailDto();
        approveEmailDto.setCode(userContact.getCode());
        approveEmailDto.setEmail(email);
        String requestBody = objectMapper.writeValueAsString(approveEmailDto);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user-contact/approve-code")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("result")));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("true")));
    }

    @Order(3)
    @Test
    void register() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setName("Danil");
        registerDto.setEmail(email);
        registerDto.setPassword(password);
        String requestBody = objectMapper.writeValueAsString(registerDto);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("token")));
        String jsonResponse = perform.andReturn().getResponse().getContentAsString();
        assertTrue(validatedToken(jsonResponse));
    }

    @Order(4)
    @Test
    void auth() throws Exception {
        EmailAuthenticationResponse emailAuthenticationResponse = new EmailAuthenticationResponse(email, password);
        String requestBody = objectMapper.writeValueAsString(emailAuthenticationResponse);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/auth")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        perform.andExpect(MockMvcResultMatchers.content()
                .string(org.hamcrest.Matchers.containsString("token")));
        String jsonResponse = perform.andReturn().getResponse().getContentAsString();
        assertTrue(validatedToken(jsonResponse));
    }

    @Order(5)
    @Test
    void deleteUserContactToDB(){
        UserContact deleteContact = userContactRepo.findByContactOrderByCodeTime(email);
        userContactRepo.delete(deleteContact);
    }

    private boolean validatedToken(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode postsNode = jsonNode.get("token");
        User user = userRepository.findUserByContact("danilcherep7@gmail.com");
        return jwtService.isTokenValid(postsNode.textValue(), user);
    }
}
