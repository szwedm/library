package com.msz.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(CreateUserRequest userRequest) {
        UserEntity userEntity = new UserEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        UserEntity user = userRepository.save(userEntity);
        return user.getId();
    }
}
