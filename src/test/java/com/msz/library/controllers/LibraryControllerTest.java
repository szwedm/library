package com.msz.library.controllers;

import com.msz.library.advices.BookAlreadyLentAdvice;
import com.msz.library.advices.BookNotFoundAdvice;
import com.msz.library.advices.BookNotLentToUserAdvice;
import com.msz.library.advices.UserNotFoundAdvice;
import com.msz.library.domain.BookEntity;
import com.msz.library.domain.UserEntity;
import com.msz.library.exceptions.BookAlreadyLentException;
import com.msz.library.exceptions.BookNotFoundException;
import com.msz.library.exceptions.BookNotLentToUserException;
import com.msz.library.requests.LibraryRequest;
import com.msz.library.responses.LibraryResponse;
import com.msz.library.security.CustomUserDetails;
import com.msz.library.services.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LibraryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LibraryService service;

    @InjectMocks
    private LibraryController controller;

    @BeforeEach
    void init () {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new BookNotFoundAdvice(), new UserNotFoundAdvice(), new BookAlreadyLentAdvice(), new BookNotLentToUserAdvice())
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();
    }

    @Test
    void test_borrow_book_successful() throws Exception {
        LibraryRequest request = LibraryRequest.create("Fake Book", "Fake Author");
        BookEntity book = new BookEntity("Fake Book", "Fake Author");
        book.setLent(true);
        UserEntity user = new UserEntity("User", "user@email.com", "pass123".toCharArray());
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "pass123", null);
        LibraryResponse response = LibraryResponse.create(book, user);

        when(service.borrowABook(anyString(), any(LibraryRequest.class))).thenReturn(response);

        mockMvc.perform(post("/library/borrow-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(token))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("bookId").value(book.getIsbn()))
                .andExpect(jsonPath("bookTitle").value(book.getName()))
                .andExpect(jsonPath("bookAuthor").value(book.getAuthor()))
                .andExpect(jsonPath("lent").value(true))
                .andExpect(jsonPath("userId").value(user.getId()));
    }

    @Test
    void test_borrow_book_unauthorized() throws Exception {
        mockMvc.perform(post("/library/borrow-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(new UsernamePasswordAuthenticationToken("User", "pass")))
                .andDo(log())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_borrow_book_BookNotFoundException() throws Exception {
        LibraryRequest request = LibraryRequest.create("Fake Book", "Fake Author");
        UserEntity user = new UserEntity("User", "user@email.com", "pass123".toCharArray());
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "pass123", null);

        when(service.borrowABook(anyString(), any(LibraryRequest.class))).thenThrow(new BookNotFoundException(request.getBookTitle()));

        mockMvc.perform(post("/library/borrow-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(token))
                .andDo(log())
                .andExpect(status().isNotFound());
    }

    @Test
    void test_borrow_book_BookAlreadyLentException() throws Exception {
        LibraryRequest request = LibraryRequest.create("Fake Book", "Fake Author");
        UserEntity user = new UserEntity("User", "user@email.com", "pass123".toCharArray());
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "pass123", null);

        when(service.borrowABook(anyString(), any(LibraryRequest.class))).thenThrow(new BookAlreadyLentException(request.getBookTitle()));

        mockMvc.perform(post("/library/borrow-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(token))
                .andDo(log())
                .andExpect(status().isConflict());
    }

    @Test
    void test_return_book_successful() throws Exception {
        LibraryRequest request = LibraryRequest.create("Fake Book", "Fake Author");
        BookEntity book = new BookEntity("Fake Book", "Fake Author");
        UserEntity user = new UserEntity("User", "user@email.com", "pass123".toCharArray());
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "pass123", null);
        LibraryResponse response = LibraryResponse.create(book, user);

        when(service.returnABook(anyString(), any(LibraryRequest.class))).thenReturn(response);

        mockMvc.perform(post("/library/return-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(token))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("bookId").value(book.getIsbn()))
                .andExpect(jsonPath("bookTitle").value(book.getName()))
                .andExpect(jsonPath("bookAuthor").value(book.getAuthor()))
                .andExpect(jsonPath("lent").value(false))
                .andExpect(jsonPath("userId").value(user.getId()));
    }

    @Test
    void test_return_book_unauthorized() throws Exception {
        mockMvc.perform(post("/library/return-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(new UsernamePasswordAuthenticationToken("User", "pass")))
                .andDo(log())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_return_book_BookNotFoundException() throws Exception {
        LibraryRequest request = LibraryRequest.create("Fake Book", "Fake Author");
        UserEntity user = new UserEntity("User", "user@email.com", "pass123".toCharArray());
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "pass123", null);

        when(service.returnABook(anyString(), any(LibraryRequest.class))).thenThrow(new BookNotFoundException(request.getBookTitle()));

        mockMvc.perform(post("/library/return-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(token))
                .andDo(log())
                .andExpect(status().isNotFound());
    }

    @Test
    void test_return_book_BookNotLentToUserException() throws Exception {
        LibraryRequest request = LibraryRequest.create("Fake Book", "Fake Author");
        UserEntity user = new UserEntity("User", "user@email.com", "pass123".toCharArray());
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "pass123", null);

        when(service.returnABook(anyString(), any(LibraryRequest.class))).thenThrow(new BookNotLentToUserException(request.getBookTitle()));

        mockMvc.perform(post("/library/return-book")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(token))
                .andDo(log())
                .andExpect(status().isBadRequest());
    }
}