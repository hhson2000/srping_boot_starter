package com.example.practice.service;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.entity.User;
import com.example.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
