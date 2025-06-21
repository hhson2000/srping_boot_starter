package com.example.practice.comonResponse;

import com.example.practice.entity.User;

import java.util.List;

public class ListUserResponse {
    private int status;
    private String message;
    private List<User> users;

    public ListUserResponse(int status, String message, List<User> users) {
        this.status = status;
        this.message = message;
        this.users = users;
    }

    public ListUserResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.users = null;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
