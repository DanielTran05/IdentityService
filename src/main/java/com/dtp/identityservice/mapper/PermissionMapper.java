package com.dtp.identityservice.mapper;

import com.dtp.identityservice.dto.request.PermissionRequest;
import com.dtp.identityservice.dto.response.PermissionResponse;
import com.dtp.identityservice.entity.Permission;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    //void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
