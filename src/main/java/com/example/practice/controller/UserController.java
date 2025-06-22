package com.example.practice.controller;

import com.example.practice.comonResponse.ApiResponse;
import com.example.practice.comonResponse.ListUserResponse;
import com.example.practice.comonResponse.UserResponse;
import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.entity.User;
import com.example.practice.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createRequest(request));
        return response;
    }

    @GetMapping
    ResponseEntity<ListUserResponse> getUsers() {
        List<User> user = userService.getAllUsers();
        return new ResponseEntity<>(new ListUserResponse(200, "Lấy danh sách user thành công", user), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return new ResponseEntity<>(new UserResponse(200, "Lấy User thành công", user), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable String userId) {
        User user = userService.updateUser(request, userId);
        return new ResponseEntity<>(new UserResponse(200, "Update User thành công", user), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<UserResponse> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new UserResponse(200, "Xóa User thành công"), HttpStatus.OK);

    }
}
