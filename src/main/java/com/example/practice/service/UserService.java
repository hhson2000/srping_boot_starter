package com.example.practice.service;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.entity.User;
import com.example.practice.exception.ErrorCode;
import com.example.practice.exception.AppException;
import com.example.practice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createRequest(UserCreationRequest request) {
        User user = new User();
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXIST, "Username already exists");
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        userRepository.save(user);
        return user;
    }

    public User updateUser(UserUpdateRequest request, String id) {
        User user = getUser(id);
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Lấy danh sách user thất bại: " + e.getMessage(), e);
        }
    }

    public User getUser(String id) {
        try {
            return userRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new RuntimeException("User không tồn tại: " + e.getMessage(), e);
        }
    }

    public void deleteUser(String id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new RuntimeException("User with ID " + id + " not found");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("User không tồn tại: " + e.getMessage(), e);
        }
    }
}
