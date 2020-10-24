package com.msz.library;

public class PasswordsDoNotMatchException extends RuntimeException {

    PasswordsDoNotMatchException() {
        super("Old password DOES NOT match entity password");
    }
}
