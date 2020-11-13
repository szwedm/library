package com.msz.library.advices;

import com.msz.library.exceptions.BookAlreadyLentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookAlreadyLentAdvice {

    @ResponseBody
    @ExceptionHandler(BookAlreadyLentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userNotFoundHandler(BookAlreadyLentException ex) {
        return ex.getMessage();
    }
}