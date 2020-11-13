package com.msz.library.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msz.library.domain.BookEntity;
import com.msz.library.domain.UserEntity;

import java.util.Objects;

public class LibraryResponse {

    private final String bookId;
    private final String bookTitle;
    private final String bookAuthor;
    private final String userId;

    @JsonCreator
    private LibraryResponse(@JsonProperty("bookId") String bookId,
                            @JsonProperty("bookTitle") String bookTitle,
                            @JsonProperty("bookAuthor") String bookAuthor,
                            @JsonProperty("userId") String userId) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getUserId() {
        return userId;
    }

    public static LibraryResponse create(BookEntity book, UserEntity user) {
        return new LibraryResponse(book.getIsbn(), book.getName(), book.getAuthor(), user.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryResponse that = (LibraryResponse) o;
        return bookId.equals(that.bookId) &&
                bookTitle.equals(that.bookTitle) &&
                bookAuthor.equals(that.bookAuthor) &&
                userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookTitle, bookAuthor, userId);
    }

    @Override
    public String toString() {
        return "LibraryResponse{" +
                "bookId='" + bookId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
