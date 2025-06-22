package com.example.practice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserUpdateRequest {
    @NotBlank(message = "Password can not blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;

    public UserUpdateRequest(String password, String firstname, String lastname, LocalDate dob) {
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
    }

}
