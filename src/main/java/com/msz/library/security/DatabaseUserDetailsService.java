package com.msz.library.security;

import com.msz.library.domain.UserEntity;
import com.msz.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = Logger.getLogger("DatabaseUserDetailsService");

    @Autowired
    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        logger.info("Found user in DB: " + userEntity.getEmail());
        return new CustomUserDetails(userEntity);
    }
}
