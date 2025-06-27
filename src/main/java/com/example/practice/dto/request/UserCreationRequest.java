package com.example.practice.dto.request;

import com.example.practice.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank(message = "Username can not blank")
    @Size(min = 6, message = "USER_SIZE")
    String username;
    @NotBlank(message = "Password can not blank")
    @Size(min = 8, message = "PASSWORD_SIZE")
    String password;
    String firstname;
    String lastname;
    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

}
