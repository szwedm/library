package com.msz.library;

public class UserAlreadyExistsException extends RuntimeException {

    UserAlreadyExistsException(String email) {
        super("User with provided email: " + email + " already exists");
    }
}
