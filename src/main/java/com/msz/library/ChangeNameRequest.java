package com.msz.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class ChangeNameRequest {

    private final String name;

    @JsonCreator
    private ChangeNameRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ChangeNameRequest create(String name) {
        return new ChangeNameRequest(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeNameRequest that = (ChangeNameRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ChangeNameRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
