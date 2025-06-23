package com.example.practice.comonResponse;

import com.example.practice.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListUserResponse {
    private int status;
    private String message;
    private List<User> users;

    public ListUserResponse(int status, String message, List<User> users) {
        this.status = status;
        this.message = message;
        this.users = users;
    }

}
