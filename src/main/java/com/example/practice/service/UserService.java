package com.example.practice.service;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.User;
import com.example.practice.enums.Role;
import com.example.practice.exception.ErrorCode;
import com.example.practice.exception.AppException;
import com.example.practice.mapper.UserMapper;
import com.example.practice.repository.RoleRepository;
import com.example.practice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXIST, "Username already exists");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        //user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UserUpdateRequest request, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(request, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        userRepository.save(user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAuthority('VIEW_REQUEST')")
    public List<UserResponse> getAllUsers() {
        log.info("Get all users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        log.info("Get user with id: {}", id);
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

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(context)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXIST));
        return userMapper.toUserResponse(user);
    }
}
