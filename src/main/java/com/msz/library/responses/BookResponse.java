package com.msz.library.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msz.library.domain.BookEntity;

import java.util.Objects;

public final class BookResponse {

    private final String isbn;
    private final String name;
    private final String author;
    private final boolean lent;

    @JsonCreator
    private BookResponse(@JsonProperty("isbn") String isbn,
                         @JsonProperty("name") String name,
                         @JsonProperty("author") String author,
                         @JsonProperty("lent") boolean lent) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.lent = lent;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isLent() {
        return lent;
    }

    public static BookResponse create(BookEntity bookEntity) {
        return new BookResponse(bookEntity.getIsbn(), bookEntity.getName(), bookEntity.getAuthor(), bookEntity.isLent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookResponse that = (BookResponse) o;
        return isbn.equals(that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", lent=" + lent +
                '}';
    }
}
