package com.example.practice.dto.request;

import com.example.practice.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank(message = "Password can not blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;
    String firstname;
    String lastname;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
