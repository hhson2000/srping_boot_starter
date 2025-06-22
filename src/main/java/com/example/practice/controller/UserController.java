package com.example.practice.controller;

import com.example.practice.comonResponse.ApiResponse;
import com.example.practice.comonResponse.ListUserResponse;
import com.example.practice.comonResponse.UserResponse;
import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.entity.User;
import com.example.practice.exception.UserException;
import com.example.practice.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        userService.createRequest(request);
        return new ResponseEntity<>(new ApiResponse(201, "Tạo user thành công"), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<ListUserResponse> getUsers() {
        try {
            List<User> user = userService.getAllUsers();
            return new ResponseEntity<>(new ListUserResponse(200, "Lấy danh sách user thành công", user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ListUserResponse(9999, "Tạo user thất bại"), HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        try {
            User user = userService.getUser(userId);
            return new ResponseEntity<>(new UserResponse(200, "Lấy User thành công", user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(9999, "Tìm kiếm user thất bại"), HttpStatus.OK);
        }
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable String userId) {
        try {
            User user = userService.updateUser(request, userId);
            return new ResponseEntity<>(new UserResponse(200, "Update User thành công", user), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new UserResponse(12001, e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(9999, "Update user thất bại"), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<UserResponse> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(new UserResponse(200, "Xóa User thành công"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(9999, "Xóa user thất bại"), HttpStatus.OK);
        }
    }
}
