package com.example.practice.mapper;

import com.example.practice.dto.request.PermissionRequest;
import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.dto.response.PermissionResponse;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.Permission;
import com.example.practice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
