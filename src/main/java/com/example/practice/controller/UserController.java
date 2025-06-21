package com.example.practice.controller;

import com.example.practice.comonResponse.ApiResponse;
import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.entity.User;
import com.example.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreationRequest request) {
        try {
            userService.createRequest(request);
            return new ResponseEntity<>(new ApiResponse(201, "Tạo user thành công"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(500, "Tạo user thất bại"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
