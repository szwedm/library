package com.msz.library;

public final class UserResponse {

    private final String id;
    private final String name;
    private final String email;
    private final boolean active;

    private UserResponse(String id, String name, String email, boolean active) {
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

    public static UserResponse create(String id, String name, String email, boolean active) {
        return new UserResponse(id, name, email, active);
    }
}