package com.msz.library.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String name) {
        super("Could not find book: " + name);
    }
}
