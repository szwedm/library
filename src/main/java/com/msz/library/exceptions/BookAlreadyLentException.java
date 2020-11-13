package com.msz.library.exceptions;

public class BookAlreadyLentException extends RuntimeException {

    public BookAlreadyLentException(String name) {
        super(name + "has been already lent");
    }
}
