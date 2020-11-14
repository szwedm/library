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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    void test_borrowABook_successful() {
        UserEntity user = new UserEntity("Fake user", "user@email.com", "pass123".toCharArray());
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        BookEntity book = new BookEntity(request.getBookTitle(), request.getBookAuthor());

        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(book));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(book);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        LibraryResponse response = libraryService.borrowABook(user.getId(), request);

        assertNotNull(response);
        assertEquals(request.getBookTitle(), response.getBookTitle());
        assertEquals(request.getBookAuthor(), response.getBookAuthor());
        assertEquals(user.getId(), response.getUserId());
        assertTrue(response.isLent());

        verify(bookRepository, times(1)).findByName(anyString());
        verify(userRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(BookEntity.class));
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void test_borrowABook_throws_BookNotFoundException() {
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        when(bookRepository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,() -> libraryService.borrowABook(anyString(), request));
    }

    @Test
    void test_borrowABook_throws_BookAlreadyLentException() {
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        BookEntity book = new BookEntity(request.getBookTitle(), request.getBookAuthor());
        book.setLent(true);
        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(book));
        assertThrows(BookAlreadyLentException.class,() -> libraryService.borrowABook(anyString(), request));
    }

    @Test
    void test_borrowABook_throws_UserNotFoundException() {
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        BookEntity book = new BookEntity(request.getBookTitle(), request.getBookAuthor());
        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(book));
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,() -> libraryService.borrowABook(anyString(), request));
    }

    @Test
    void test_returnABook_successful() {
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        BookEntity book = new BookEntity(request.getBookTitle(), request.getBookAuthor());
        UserEntity user = new UserEntity("Fake user", "user@email.com", "pass123".toCharArray());
        user.getBorrowedBooks().add(book);

        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(book));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(book);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        LibraryResponse response = libraryService.returnABook(user.getId(), request);

        assertNotNull(response);
        assertEquals(request.getBookTitle(), response.getBookTitle());
        assertEquals(request.getBookAuthor(), response.getBookAuthor());
        assertEquals(user.getId(), response.getUserId());
        assertFalse(response.isLent());
        assertFalse(book.isLent());
        assertTrue(user.getBorrowedBooks().isEmpty());

        verify(bookRepository, times(1)).findByName(anyString());
        verify(userRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(BookEntity.class));
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void test_returnABook_throws_BookNotFoundException() {
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        when(bookRepository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,() -> libraryService.returnABook(anyString(), request));
    }

    @Test
    void test_returnABook_throws_UserNotFoundException() {
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        BookEntity book = new BookEntity(request.getBookTitle(), request.getBookAuthor());
        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(book));
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,() -> libraryService.returnABook(anyString(), request));
    }

    @Test
    void test_returnABook_throws_BookNotLentToUserException() {
        LibraryRequest request = LibraryRequest.create("Fake book", "Fake author");
        BookEntity book = new BookEntity(request.getBookTitle(), request.getBookAuthor());
        UserEntity user = new UserEntity("Fake user", "user@email.com", "pass123".toCharArray());
        book.setLent(true);
        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(book));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        assertThrows(BookNotLentToUserException.class,() -> libraryService.returnABook(user.getId(), request));
    }
}