package com.msz.library.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super("User with provided email: " + email + " already exists");
    }
}
