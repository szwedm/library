package com.msz.library.services;

import com.msz.library.requests.CreateUserRequest;
import com.msz.library.domain.UserEntity;
import com.msz.library.responses.UserResponse;
import com.msz.library.exceptions.UserAlreadyExistsException;
import com.msz.library.exceptions.UserNotFoundException;
import com.msz.library.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void test_create_user_successful() {
        CreateUserRequest userRequest = CreateUserRequest.create(
                "Fake User",
                "fake.user@mail.com",
                "fakepass".toCharArray());

        UserEntity entity = new UserEntity(
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword());

        when(passwordEncoder.encode(anyString())).thenReturn("fakepass");
        when(repository.save(any(UserEntity.class))).thenReturn(entity);

        UserResponse userResponse = service.createUser(userRequest);
        assertNotNull(userResponse);
        assertEquals(userRequest.getName(), userResponse.getName());
        assertEquals(userRequest.getEmail(), userResponse.getEmail());

        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void test_create_user_throws_UserAlreadyExistsException() {
        CreateUserRequest userRequest = CreateUserRequest.create(
                "Fake User",
                "fake.user@mail.com",
                "fakepass".toCharArray());

        UserEntity entity = new UserEntity(
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword());

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        assertThrows(UserAlreadyExistsException.class, () -> service.createUser(userRequest));
    }

    @Test
    void test_get_all_users() {
        UserEntity user1 = new UserEntity("Mr User", "mruser@mail.com", "password123".toCharArray());
        UserEntity user2 = new UserEntity("Mrs User", "mrsuser@mail.com", "123password".toCharArray());
        List<UserEntity> userData = new ArrayList<>();
        userData.add(user1);
        userData.add(user2);

        when(repository.findAll()).thenReturn(userData);
        assertEquals(service.getAllUsers().size(), userData.size());

        verify(repository, times(1)).findAll();
    }

    @Test
    void test_get_user_successful() {
        UserEntity userEntity = new UserEntity("User", "user@mail.com", "password123".toCharArray());

        when(repository.findById(anyString())).thenReturn(Optional.of(userEntity));
        UserResponse userResponse = service.getUser(anyString());

        assertNotNull(userResponse);
        assertEquals(userEntity.getName(), userResponse.getName());
        assertEquals(userEntity.getEmail(), userResponse.getEmail());

        verify(repository, times(1)).findById(anyString());
    }

    @Test
    void test_get_user_throws_UserNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.getUser(anyString()));
    }

    @Test
    void test_activate_user_successful() {
        UserEntity userEntity = new UserEntity("User", "user@mail.com", "password123".toCharArray());

        when(repository.findById(anyString())).thenReturn(Optional.of(userEntity));
        service.activateUser(anyString());

        assertTrue(repository.findById(anyString()).get().isActive());

        verify(repository, times(2)).findById(anyString());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void test_activate_user_throws_UserNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.deactivateUser(anyString()));
    }

    @Test
    void test_deactivate_user_successful() {
        UserEntity userEntity = new UserEntity("User", "user@mail.com", "password123".toCharArray());
        ReflectionTestUtils.setField(userEntity, "active", true);

        when(repository.findById(anyString())).thenReturn(Optional.of(userEntity));

        service.deactivateUser(anyString());
        assertFalse(repository.findById(anyString()).get().isActive());

        verify(repository, times(2)).findById(anyString());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void test_deactivate_user_throws_UserNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.deactivateUser(anyString()));
    }
}
