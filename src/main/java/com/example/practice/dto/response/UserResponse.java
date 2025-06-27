package com.example.practice.dto.response;

import com.example.practice.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
     String id;
     String username;
     String firstname;
     String lastname;
     LocalDate dob;
     Set<RoleResponse> roles;
}
