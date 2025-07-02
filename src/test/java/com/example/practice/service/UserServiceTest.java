package com.example.practice.service;


import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.User;
import com.example.practice.exception.AppException;
import com.example.practice.mapper.UserMapper;
import com.example.practice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserUpdateRequest updateRequest;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest.builder()
                .username("john")
                .firstname("John")
                .lastname("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        updateRequest = UserUpdateRequest.builder()
                .firstname("John2")
                .lastname("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("cf0600f538b3")
                .username("john")
                .firstname("John")
                .lastname("Doe")
                .dob(dob)
                .build();

        user = User.builder()
                .id("cf0600f538b3")
                .username("john")
                .firstname("John")
                .lastname("Doe")
                .dob(dob)
                .build();

        User existingUser = new User();
        existingUser.setId("hasdhfashdufhawewqef");
        existingUser.setUsername("john2");
        existingUser.setFirstname("John2");
        existingUser.setLastname("Doe2");
        existingUser.setDob(dob);
        existingUser.setRoles(new HashSet<>());
        existingUser.setPassword("oldPassword");
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createRequest(request);
        // THEN

        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class,
                () -> userService.createRequest(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode())
                .isEqualTo(3001);
    }

//    @Test
//    void updateUser_validRequest_success() {
//
//        when(userRepository.existsById(anyString())).thenReturn(false);
//
//        var response = userService.updateUser(updateRequest, "hasdhfashdufhawewqef");
//
//        Assertions.assertThat(response.getUsername()).isEqualTo("john2");
//
//    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        var response = userService.getMyInfo();
        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_failed() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
        var exception = assertThrows(AppException.class,
                () -> userService.getMyInfo());
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(3001);
    }
}