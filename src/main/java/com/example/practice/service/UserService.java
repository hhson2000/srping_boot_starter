package com.example.practice.service;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.User;
import com.example.practice.enums.Role;
import com.example.practice.exception.ErrorCode;
import com.example.practice.exception.AppException;
import com.example.practice.mapper.UserMapper;
import com.example.practice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXIST, "Username already exists");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UserUpdateRequest request, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(request, user);

        userRepository.save(user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponseList(users);
    }

    public UserResponse getUser(String id) {

        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));

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
