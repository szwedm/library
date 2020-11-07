package com.msz.library.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Objects;

public final class CreateUserRequest {

    @NotBlank(message = "name is required")
    private final String name;
    @NotBlank(message = "email is required")
    @Email
    private final String email;
    @NotEmpty(message = "password is required")
    private final char[] password;

    @JsonCreator
    private CreateUserRequest(@JsonProperty("name") String name,
                              @JsonProperty("email") String email,
                              @JsonProperty("password") char[] password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }

    public static CreateUserRequest create(String name, String email, char[] password) {
        return new CreateUserRequest(name, email, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserRequest that = (CreateUserRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                '}';
    }
}