package com.example.practice.controller;

import com.example.practice.comonResponse.ApiResponse;
import com.example.practice.comonResponse.ListUserResponse;
import com.example.practice.comonResponse.UserResponse;
import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.entity.User;
import com.example.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreationRequest request) {
        try {
            userService.createRequest(request);
            return new ResponseEntity<>(new ApiResponse(201, "Tạo user thành công"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(9999, "Tạo user thất bại"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    ResponseEntity<ListUserResponse> getUsers() {
        try {
            List<User> user = userService.getAllUsers();
            return new ResponseEntity<>(new ListUserResponse(200, "Lấy danh sách user thành công" ,user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ListUserResponse(9999, "Tạo user thất bại"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        try {
            User user = userService.getUser(userId);
            return new ResponseEntity<>(new UserResponse(200, "Lấy User thành công" ,user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(9999, "Tìm kiếm user thất bại"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable String userId) {
        try {
            User user = userService.updateUser(request, userId);
            return new ResponseEntity<>(new UserResponse(200, "Update User thành công" ,user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(9999, "Update user thất bại"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
