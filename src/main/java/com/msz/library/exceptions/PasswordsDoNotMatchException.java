package com.msz.library.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException() {
        super("Old password DOES NOT match entity password");
    }
}
