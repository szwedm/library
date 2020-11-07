package com.msz.library.services;

import com.msz.library.domain.*;
import com.msz.library.exceptions.PasswordsDoNotMatchException;
import com.msz.library.exceptions.UserAlreadyExistsException;
import com.msz.library.exceptions.UserNotFoundException;
import com.msz.library.repositories.UserRepository;
import com.msz.library.requests.ChangeNameRequest;
import com.msz.library.requests.ChangePasswordRequest;
import com.msz.library.requests.CreateUserRequest;
import com.msz.library.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest userRequest) {
        userRepository.findByEmail(userRequest.getEmail()).ifPresent((existingUser) -> {
            throw new UserAlreadyExistsException(existingUser.getEmail());
        });
        UserEntity userEntity = new UserEntity(
                userRequest.getName(),
                userRequest.getEmail(),
                passwordEncoder.encode(new String(userRequest.getPassword())).toCharArray());
        UserEntity user = userRepository.save(userEntity);
        return UserResponse.create(user);
    }

    public UserResponse changeUsername(ChangeNameRequest nameRequest) {
        // TO DO
        return null;
    }

    public UserResponse changeUserPassword(String id, ChangePasswordRequest passwordRequest) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (passwordEncoder.matches(new String(passwordRequest.getOldPassword()), new String(userEntity.getPassword()))) {
            userEntity.setPassword(passwordEncoder.encode(new String(passwordRequest.getNewPassword())).toCharArray());
            return UserResponse.create(userRepository.save(userEntity));
        } else {
            throw new PasswordsDoNotMatchException();
        }
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::create)
                .collect(Collectors.toUnmodifiableList());
    }

    public UserResponse getUser(String id) {
        return UserResponse.create(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    public UserEntity deactivateUser(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userEntity.setActive(false);
        return userRepository.save(userEntity);
    }

    public UserEntity activateUser(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userEntity.setActive(true);
        return userRepository.save(userEntity);
    }
}
