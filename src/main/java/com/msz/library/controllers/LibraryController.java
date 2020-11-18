package com.msz.library.controllers;

import com.msz.library.requests.LibraryRequest;
import com.msz.library.responses.LibraryResponse;
import com.msz.library.security.CustomUserDetails;
import com.msz.library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;
    private static final Logger logger = Logger.getLogger("LibraryController");

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/borrow-book")
    ResponseEntity<LibraryResponse> borrowABook(@Valid @RequestBody LibraryRequest request, Authentication auth) {
        logger.info("POST request - borrow a book");
        if (auth.isAuthenticated()) {
            CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity<>(libraryService.borrowABook(user.getId(), request), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/return-book")
    ResponseEntity<LibraryResponse> returnABook(@Valid @RequestBody LibraryRequest request, Authentication auth) {
        logger.info("POST request - return a book");
        if (auth.isAuthenticated()) {
            CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity<>(libraryService.returnABook(user.getId(), request), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
