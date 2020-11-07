package com.msz.library.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public final class AddBookRequest {

    @NotBlank(message = "Book name is required")
    private final String name;
    @NotBlank(message = "Book's author name is required")
    private final String author;

    @JsonCreator
    private AddBookRequest(@JsonProperty("name") String name,
                           @JsonProperty("author") String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public static AddBookRequest create(String name, String author) {
        return new AddBookRequest(name, author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddBookRequest that = (AddBookRequest) o;
        return name.equals(that.name) &&
                author.equals(that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author);
    }

    @Override
    public String toString() {
        return "AddBookRequest{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
