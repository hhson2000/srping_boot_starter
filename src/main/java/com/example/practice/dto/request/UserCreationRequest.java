package com.example.practice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserCreationRequest {
    @NotBlank(message = "Username can not blank")
    @Size(min = 8, message = "USER_SIZE")
    private String username;
    @NotBlank(message = "Password can not blank")
    @Size(min = 8, message = "PASSWORD_SIZE")
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;

}
