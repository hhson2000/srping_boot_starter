package com.example.practice.controller;

import com.example.practice.comonResponse.ApiResponse;
import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserController userController;

    @MockitoBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse expectedResponse;
    private ApiResponse<UserResponse> response;

    @BeforeEach
    void setUp() {
        request = new UserCreationRequest();
        expectedResponse = new UserResponse();
        userController = new UserController(userService);
    }

    @Test
    void createUser_Success() {
        // Arrange
        UserCreationRequest request = new UserCreationRequest();
        UserResponse expectedResponse = new UserResponse();
        when(userService.createRequest(any(UserCreationRequest.class))).thenReturn(expectedResponse);

        // Act
        ApiResponse<UserResponse> response = userController.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response.getResult());
    }


    @Test
    void createUser_Fail() {
        // Arrange
        UserCreationRequest request = new UserCreationRequest();
        when(userService.createRequest(any(UserCreationRequest.class))).thenThrow(new RuntimeException("Creation failed"));

        // Act & Assert
        try {
            ApiResponse<UserResponse> response = userController.createUser(request);
        } catch (RuntimeException e) {
            assertEquals("Creation failed", e.getMessage());
        }
    }

    @Test
    void getUsers_Success() throws Exception {
        // Arrange
        List<UserResponse> expectedUsers = List.of(new UserResponse(), new UserResponse());
        when(userService.getAllUsers()).thenReturn(expectedUsers);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testUser");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        // Act
        ApiResponse<List<UserResponse>> response = userController.getUsers();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getResult());
        assertEquals(2, response.getResult().size());
        assertEquals(expectedUsers, response.getResult());
    }
}
