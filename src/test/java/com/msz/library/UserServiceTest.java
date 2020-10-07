package com.msz.library;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    UserService service;

    @Mock
    UserRepository repository;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new UserService(repository);
    }

    @BeforeEach
    void preTest() {
        reset(repository);
    }

    @Test
    void createUser() {
        CreateUserRequest userRequest = CreateUserRequest.create(
                "Fake User",
                "fake.user@mail.com",
                "fakepass".toCharArray());

        UserEntity entity = new UserEntity(
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword());

        UserResponse response = UserResponse.create(entity);

        when(repository.save(any())).thenReturn(entity);
        UserEntity savedUser = repository.save(entity);
        UserResponse createdResponse = UserResponse.create(savedUser);

        assertEquals(response, createdResponse);
        verify(repository, times(1)).save(any());
    }

    @Test
    void getAllUsers() {
        UserEntity user1 = new UserEntity("Mr User", "mruser@mail.com", "password123".toCharArray());
        UserEntity user2 = new UserEntity("Mrs User", "mrsuser@mail.com", "123password".toCharArray());
        List<UserEntity> userData = new ArrayList<>();
        userData.add(user1);
        userData.add(user2);

        when(repository.findAll()).thenReturn(userData);
        List<UserEntity> users = repository.findAll();

        assertEquals(users.size(), 2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getUser() {
        UserEntity user = new UserEntity("User", "user@mail.com", "password123".toCharArray());

        when(repository.findById(anyString())).thenReturn(Optional.of(user));
        UserEntity foundUser = repository.findById("ffff").get();

        assertEquals(user, foundUser);
        verify(repository, times(1)).findById("ffff");
    }

    @Test
    void deactivateUser() {
        UserEntity user = new UserEntity("User", "user@mail.com", "password123".toCharArray());

        when(repository.findById(anyString())).thenReturn(Optional.of(user));
        UserEntity foundUser = repository.findById("ffff").get();

        assertEquals(user, foundUser);
        verify(repository, times(1)).findById("ffff");

        foundUser.setActive(false);

        when(repository.save(any())).thenReturn(foundUser);
        UserEntity deactivatedUser = repository.save(foundUser);

        assertEquals(foundUser, deactivatedUser);
        verify(repository, times(1)).save(any());
    }
}