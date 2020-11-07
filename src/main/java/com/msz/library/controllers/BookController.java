package com.msz.library.controllers;

import com.msz.library.requests.AddBookRequest;
import com.msz.library.requests.RemoveBookRequest;
import com.msz.library.responses.BookResponse;
import com.msz.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private static final Logger logger = Logger.getLogger("BookController");

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    ResponseEntity<BookResponse> addBook(@Valid @RequestBody AddBookRequest request, Authentication auth) {
        logger.info("POST request - add book to library");
        if (auth.getName().equals("admin")) {
            return new ResponseEntity<>(bookService.addBook(request), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    void removeBook(@Valid @RequestBody RemoveBookRequest request, Authentication auth) {
        logger.info("DELETE request - remove book from library");
        if (auth.getName().equals("admin")) {
            bookService.removeBook(request);
        }
    }
}
