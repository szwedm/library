package com.msz.library;

public class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String id) {
        super("Could not find user with id: " + id);
    }
}
