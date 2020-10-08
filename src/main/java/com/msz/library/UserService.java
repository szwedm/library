package com.msz.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest userRequest) {
        userRepository.findByEmail(userRequest.getEmail()).ifPresent((existingUser) -> {
            throw new UserAlreadyExistsException(existingUser.getEmail());
        });
        UserEntity userEntity = new UserEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        UserEntity user = userRepository.save(userEntity);
        return UserResponse.create(user);
    }

    public UserResponse changeUsername(ChangeNameRequest nameRequest) {
        // TO DO
        return null;
    }

    public UserResponse changeUserPassword(ChangePasswordRequest passwordRequest) {
        // TO DO
        return null;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::create)
                .collect(Collectors.toUnmodifiableList());
    }

    public UserResponse getUser(String id) {
        return UserResponse.create(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    public void deactivateUser(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userEntity.setActive(false);
        userRepository.save(userEntity);
    }
}
