package com.msz.library.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "user")
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(unique = true, nullable = false)
    private String id;
    private String name;
    private String email;
    private char[] password;
    private boolean active;
    @OneToMany
    private final List<BookEntity> borrowedBooks = new ArrayList<>();

    protected UserEntity() {
    }

    public UserEntity(String name, String email, char[] password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() { return password; }

    public List<BookEntity> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return id.equals(userEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
