package com.msz.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest userRequest) {
        UserEntity userEntity = new UserEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        UserEntity user = userRepository.save(userEntity);
        return UserResponse.create(user);
    }

    public UserResponse changeUsername(ChangeNameRequest nameRequest) {
        // TO DO
        // UserEntity userEntity = userRepository.findByName();
        return null;
    }

    public UserResponse changeUserPassword(ChangePasswordRequest passwordRequest) {
        // TO DO
        // UserEntity userEntity = userRepository.findBy...();
        return null;
    }

    public List<UserResponse> getAllUsers() {
        List<UserEntity> userEntities = new ArrayList<>(userRepository.findAll());
        List<UserResponse> usersResponse = new ArrayList<>(userEntities.size());
        userEntities.forEach(userEntity -> {
            usersResponse.add(UserResponse.create(userEntity));
        });
        return usersResponse;
    }
}
