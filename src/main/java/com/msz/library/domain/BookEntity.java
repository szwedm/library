package com.msz.library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "book")
@Table(name = "books")
public class BookEntity {

    @Id
    @Column(unique = true, nullable = false)
    private String isbn;
    private String name;
    private String author;
    private boolean lent;

    protected BookEntity() {
    }

    public BookEntity(String name, String author) {
        this.isbn = UUID.randomUUID().toString();
        this.name = name;
        this.author = author;
        this.lent = false;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String id) {
        this.isbn = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isLent() {
        return lent;
    }

    public void setLent(boolean lent) {
        this.lent = lent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return isbn.equals(that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
