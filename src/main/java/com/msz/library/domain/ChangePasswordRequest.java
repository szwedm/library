package com.msz.library.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public final class ChangePasswordRequest {

    private final char[] oldPassword;
    private final char[] newPassword;

    @JsonCreator
    private ChangePasswordRequest(@JsonProperty("oldPassword") char[] oldPassword,
                                  @JsonProperty("newPassword") char[] newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public char[] getOldPassword() {
        return oldPassword;
    }

    public char[] getNewPassword() {
        return newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePasswordRequest that = (ChangePasswordRequest) o;
        return Arrays.equals(oldPassword, that.oldPassword) &&
                Arrays.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(oldPassword);
        result = 31 * result + Arrays.hashCode(newPassword);
        return result;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "oldPassword=" + Arrays.toString(oldPassword) +
                ", newPassword=" + Arrays.toString(newPassword) +
                '}';
    }
}
