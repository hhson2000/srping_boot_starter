package com.example.practice.controller;

import com.example.practice.comonResponse.ApiResponse;
import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.User;
import com.example.practice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.createRequest(request));
        return response;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(
                "Role: {}", grantedAuthority.getAuthority()
        ));
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        List<UserResponse> users = userService.getAllUsers();
        response.setResult(users);
        return response;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        UserResponse user = userService.getUser(userId);
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(user);
        return response;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable String userId) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        UserResponse user = userService.updateUser(request, userId);
        response.setResult(user);
        return response;
    }

    @DeleteMapping("/{userId}")
    ApiResponse<User> deleteUser(@PathVariable String userId) {
        ApiResponse<User> response = new ApiResponse<>();
        userService.deleteUser(userId);
        response.setMessage("Xóa user thành công");
        return response;
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
