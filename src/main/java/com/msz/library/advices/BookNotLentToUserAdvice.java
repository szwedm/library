package com.msz.library.advices;

import com.msz.library.exceptions.BookNotLentToUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookNotLentToUserAdvice {

    @ResponseBody
    @ExceptionHandler(BookNotLentToUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userNotFoundHandler(BookNotLentToUserException ex) {
        return ex.getMessage();
    }
}
