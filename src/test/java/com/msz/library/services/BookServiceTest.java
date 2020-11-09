package com.msz.library.services;

import com.msz.library.domain.BookEntity;
import com.msz.library.exceptions.BookNotFoundException;
import com.msz.library.repositories.BookRepository;
import com.msz.library.requests.AddBookRequest;
import com.msz.library.requests.RemoveBookRequest;
import com.msz.library.responses.BookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    void test_add_book_successful() {
        AddBookRequest bookRequest = AddBookRequest.create(
                "Fake Book",
                "Fake Author");

        BookEntity bookEntity = new BookEntity(bookRequest.getName(), bookRequest.getAuthor());

        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        BookResponse bookResponse = bookService.addBook(bookRequest);

        assertNotNull(bookResponse);
        assertEquals(bookRequest.getName(), bookResponse.getName());
        assertEquals(bookRequest.getAuthor(), bookResponse.getAuthor());
        verify(bookRepository, times(1)).save(any(BookEntity.class));
    }

    @Test
    void test_remove_book_successful() {
        RemoveBookRequest bookRequest = RemoveBookRequest.create(
                "Fake Book",
                "Fake Author");

        BookEntity bookEntity = new BookEntity(bookRequest.getName(), bookRequest.getAuthor());

        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(bookEntity));
        bookService.removeBook(bookRequest);

        verify(bookRepository, times(1)).findByName(anyString());
        verify(bookRepository, times(1)).delete(any(BookEntity.class));
    }

    @Test
    void test_remove_book_throws_BookNotFoundException() {
        when(bookRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.removeBook(RemoveBookRequest.create("Fake Book", "Fake Author")));
    }
}