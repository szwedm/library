package com.msz.library.exceptions;

public class BookNotLentToUserException extends RuntimeException {

    public BookNotLentToUserException(String name) {
        super("User did not borrow book: " + name);
    }
}
