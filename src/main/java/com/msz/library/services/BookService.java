package com.msz.library.services;

import com.msz.library.requests.AddBookRequest;
import com.msz.library.domain.BookEntity;
import com.msz.library.responses.BookResponse;
import com.msz.library.requests.RemoveBookRequest;
import com.msz.library.exceptions.BookNotFoundException;
import com.msz.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse addBook(AddBookRequest request) {
        BookEntity bookEntity = new BookEntity(request.getName(), request.getAuthor());
        BookEntity savedBook = bookRepository.save(bookEntity);
        return BookResponse.create(savedBook);
    }

    public void removeBook(RemoveBookRequest request) {
        BookEntity bookToRemove = bookRepository.findByName(request.getName()).orElseThrow(() -> new BookNotFoundException(request.getName()));
        bookRepository.delete(bookToRemove);
    }
}
