package com.msz.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Primary
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private static final Logger logger = Logger.getLogger("UserService");

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserResponse createUser(CreateUserRequest userRequest) {
        userRepository.findByEmail(userRequest.getEmail()).ifPresent((existingUser) -> {
            throw new UserAlreadyExistsException(existingUser.getEmail());
        });
        UserEntity userEntity = new UserEntity(userRequest.getName(),
                userRequest.getEmail(),
                encoder.encode(userRequest.getPassword().toString()).toCharArray());
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        logger.info("Found username in DB: " + userEntity.getEmail());
        return new CustomUserDetails(userEntity);
    }
}
