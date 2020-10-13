package com.msz.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void test_getAllUsers_successful() throws Exception {
        List<UserEntity> entities = new ArrayList<>();
        entities.add(new UserEntity("User1", "user1@email.com", "password1".toCharArray()));
        entities.add(new UserEntity("User2", "user2@email.com", "password2".toCharArray()));

        List<UserResponse> userResponses = entities.stream()
                .map(UserResponse::create)
                .collect(Collectors.toUnmodifiableList());

        when(service.getAllUsers()).thenReturn(userResponses);

        mockMvc.perform(get("/users"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("user1@email.com"))
                .andExpect(jsonPath("$[1].email").value("user2@email.com"));

        verify(service, times(1)).getAllUsers();
    }
}