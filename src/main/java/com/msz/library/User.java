package com.msz.library;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class User implements Serializable {

    @Id
    @Column(unique = true, nullable = false)
    private String id;

    private String name;
    private String email;
    private char[] pass;
    private boolean active;

    protected User() {
    }

    public User(String name, String email, String pass) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.pass = pass.toCharArray();
        this.active = true;
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

    public char[] getPass() {
        return pass;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
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
