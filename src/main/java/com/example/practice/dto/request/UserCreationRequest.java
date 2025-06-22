package com.example.practice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserCreationRequest {
    private String username;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;

}
