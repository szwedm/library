package com.msz.library.controllers;

import com.msz.library.advices.BookNotFoundAdvice;
import com.msz.library.domain.BookEntity;
import com.msz.library.requests.AddBookRequest;
import com.msz.library.requests.RemoveBookRequest;
import com.msz.library.responses.BookResponse;
import com.msz.library.services.BookService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService service;

    @InjectMocks
    private BookController controller;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new BookNotFoundAdvice())
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();
    }

    @Test
    void test_addBook_successful() throws Exception {
        BookEntity book = new BookEntity("Fake Book", "Fake Author");
        BookResponse response = BookResponse.create(book);

        when(service.addBook(any(AddBookRequest.class))).thenReturn(response);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(new UsernamePasswordAuthenticationToken("admin","")))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Fake Book"))
                .andExpect(jsonPath("$.author").value("Fake Author"));

        verify(service, times(1)).addBook(any(AddBookRequest.class));
    }

    @Test
    void test_addBook_unauthorized() throws Exception {
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(new UsernamePasswordAuthenticationToken("guest","password123")))
                .andDo(log())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_removeBook_successful() throws Exception {
        doNothing().when(service).removeBook(any(RemoveBookRequest.class));

        mockMvc.perform(delete("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"name\":\"Fake Book\",\"author\":\"Fake Author\"}")
                .principal(new UsernamePasswordAuthenticationToken("admin","")))
                .andDo(log())
                .andExpect(status().isOk());

        verify(service, times(1)).removeBook(any(RemoveBookRequest.class));
    }
}