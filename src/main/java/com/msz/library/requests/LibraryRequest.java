package com.msz.library.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class LibraryRequest {

    @NotBlank(message = "Book title is required")
    private final String bookTitle;
    @NotBlank(message = "Book author is required")
    private final String bookAuthor;

    @JsonCreator
    private LibraryRequest(@JsonProperty("title") String bookTitle,
                           @JsonProperty("author") String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public static LibraryRequest create(String bookTitle, String bookAuthor) {
        return new LibraryRequest(bookTitle, bookAuthor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryRequest that = (LibraryRequest) o;
        return bookTitle.equals(that.bookTitle) &&
                bookAuthor.equals(that.bookAuthor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookTitle, bookAuthor);
    }

    @Override
    public String toString() {
        return "LibraryRequest{" +
                "bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                '}';
    }
}
