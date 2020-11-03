package com.msz.library.controllers;

import com.msz.library.advices.UserAlreadyExistsAdvice;
import com.msz.library.advices.UserNotFoundAdvice;
import com.msz.library.domain.CreateUserRequest;
import com.msz.library.domain.UserEntity;
import com.msz.library.domain.UserResponse;
import com.msz.library.exceptions.UserAlreadyExistsException;
import com.msz.library.exceptions.UserNotFoundException;
import com.msz.library.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new UserNotFoundAdvice(), new UserAlreadyExistsAdvice())
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();
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

        mockMvc.perform(get("/users")
                .principal(new UsernamePasswordAuthenticationToken("admin", "")))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("user1@email.com"))
                .andExpect(jsonPath("$[1].email").value("user2@email.com"));

        verify(service, times(1)).getAllUsers();
    }

    @Test
    void test_createUser_successful() throws Exception {
        CreateUserRequest userRequest = CreateUserRequest.create("User", "user@email.com", "password".toCharArray());
        UserEntity userEntity = new UserEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        UserResponse userResponse = UserResponse.create(userEntity);

        when(service.createUser(any(CreateUserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\":\"User\",\"email\":\"user@email.com\",\"password\":\"password\"}")
                .principal(new UsernamePasswordAuthenticationToken("admin", "")))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("user@email.com"));

        verify(service, times(1)).createUser(any(CreateUserRequest.class));
    }

    @Test
    void test_createUser_userAlreadyExists() throws Exception {
        CreateUserRequest userRequest = CreateUserRequest.create("User", "user@email.com", "password".toCharArray());

        when(service.createUser(any(CreateUserRequest.class))).thenThrow(new UserAlreadyExistsException(userRequest.getEmail()));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\":\"User\",\"email\":\"user@email.com\",\"password\":\"password\"}")
                .principal(new UsernamePasswordAuthenticationToken("admin", "")))
                .andDo(log())
                .andExpect(status().isConflict());
    }

    @Test
    void test_getUser_successful() throws Exception {
        UserEntity userEntity = new UserEntity("User", "user@email.com", "password".toCharArray());
        UserResponse userResponse = UserResponse.create(userEntity);

        when(service.getUser(anyString())).thenReturn(userResponse);

        mockMvc.perform(get("/users/{id}", userEntity.getId())
                .principal(new UsernamePasswordAuthenticationToken("admin", "")))
                .andDo(log())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userEntity.getId()));

        verify(service, times(1)).getUser(anyString());
    }

    @Test
    void test_getUser_userNotFound() throws Exception {
        when(service.getUser(anyString())).thenThrow(new UserNotFoundException(anyString()));

        mockMvc.perform(get("/users/{id}", "123")
                .principal(new UsernamePasswordAuthenticationToken("admin", "")))
                .andDo(log())
                .andExpect(status().isNotFound());
    }

    @Test
    void test_deleteUser_successful() throws Exception {
        UserEntity userEntity = new UserEntity("User", "user@email.com", "password".toCharArray());
        userEntity.setActive(true);

        when(service.deactivateUser(anyString())).then(invocation -> {
            userEntity.setActive(false);
            return userEntity;
        });

        mockMvc.perform(delete("/users/{id}", userEntity.getId())
                .principal(new UsernamePasswordAuthenticationToken("admin", "")))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(result -> assertFalse(userEntity.isActive()));

        verify(service, times(1)).deactivateUser(anyString());
    }

    @Test
    void test_deleteUser_userNotFound() throws Exception {
        when(service.deactivateUser(anyString())).thenThrow(new UserNotFoundException(anyString()));

        mockMvc.perform(delete("/users/{id}", "123")
                .principal(new UsernamePasswordAuthenticationToken("admin", "")))
                .andDo(log())
                .andExpect(status().isNotFound());
    }
}