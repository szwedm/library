package com.msz.library.services;

import com.msz.library.domain.BookEntity;
import com.msz.library.domain.UserEntity;
import com.msz.library.exceptions.BookAlreadyLentException;
import com.msz.library.exceptions.BookNotFoundException;
import com.msz.library.exceptions.BookNotLentToUserException;
import com.msz.library.exceptions.UserNotFoundException;
import com.msz.library.repositories.BookRepository;
import com.msz.library.repositories.UserRepository;
import com.msz.library.requests.LibraryRequest;
import com.msz.library.responses.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LibraryService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public LibraryResponse borrowABook(String userId, LibraryRequest request) {
        BookEntity bookToLend = bookRepository.findByName(request.getBookTitle()).orElseThrow(() -> new BookNotFoundException(request.getBookTitle()));
        if (bookToLend.isLent()) throw new BookAlreadyLentException(bookToLend.getName());
        UserEntity borrowingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        borrowingUser.getBorrowedBooks().add(bookToLend);
        bookToLend.setLent(true);
        BookEntity savedBook = bookRepository.save(bookToLend);
        UserEntity savedUser = userRepository.save(borrowingUser);
        return LibraryResponse.create(savedBook, savedUser);
    }

    public LibraryResponse returnABook(String userId, LibraryRequest request) {
        BookEntity bookToReturn = bookRepository.findByName(request.getBookTitle()).orElseThrow(() -> new BookNotFoundException(request.getBookTitle()));
        UserEntity returningUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (!(returningUser.getBorrowedBooks().contains(bookToReturn))) throw new BookNotLentToUserException(bookToReturn.getName());
        returningUser.getBorrowedBooks().remove(bookToReturn);
        bookToReturn.setLent(false);
        BookEntity savedBook = bookRepository.save(bookToReturn);
        UserEntity savedUser = userRepository.save(returningUser);
        return LibraryResponse.create(savedBook, savedUser);
    }
}
