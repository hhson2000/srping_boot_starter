package com.example.practice.mapper;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    @Mapping(target = "roles", ignore = true)
    void updateUser(UserUpdateRequest request,@MappingTarget User user);
    UserResponse toUserResponse(User use);
}
