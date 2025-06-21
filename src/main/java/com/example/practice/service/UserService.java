package com.example.practice.service;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.entity.User;
import com.example.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createRequest(UserCreationRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setDob(request.getDob());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());

            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Tạo user thất bại: " + e.getMessage(), e);
        }
    }

    public User updateUser(UserUpdateRequest request, String id) {
        try {
            User user = getUser(id);
            user.setPassword(request.getPassword());
            user.setDob(request.getDob());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());

            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Update user thất bại: " + e.getMessage(), e);
        }
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
}
