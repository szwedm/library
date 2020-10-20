package com.msz.library;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public String getPassword() {
        return passwordEncoder.encode(userEntity.getPassword());
    }

    @Override
    public boolean isEnabled() {
        return !(userEntity.isActive());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
