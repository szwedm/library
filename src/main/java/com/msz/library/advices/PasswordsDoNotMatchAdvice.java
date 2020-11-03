package com.msz.library.advices;

import com.msz.library.exceptions.PasswordsDoNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PasswordsDoNotMatchAdvice {

    @ResponseBody
    @ExceptionHandler(PasswordsDoNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String passwordsDoNotMatch(PasswordsDoNotMatchException ex) {
        return ex.getMessage();
    }
}