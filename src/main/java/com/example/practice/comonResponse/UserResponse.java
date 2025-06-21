package com.example.practice.comonResponse;

import com.example.practice.entity.User;

import java.util.List;

public class UserResponse {
    private int status;
    private String message;
    private User users;

    public UserResponse(int status, String message, User users) {
        this.status = status;
        this.message = message;
        this.users = users;
    }

    public UserResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}
