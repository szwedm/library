package com.msz.library;

public final class CreateUserRequest {

    private final String name;
    private final String email;
    private final char[] password;

    private CreateUserRequest(String name, String email, char[] password) {
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
}