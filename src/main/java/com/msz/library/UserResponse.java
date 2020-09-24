package com.msz.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class UserResponse {

    private final String id;
    private final String name;
    private final String email;
    private final boolean active;

    @JsonCreator
    private UserResponse(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("email") String email, @JsonProperty("active") boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public static UserResponse create(UserEntity userEntity) {
        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.isActive());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}