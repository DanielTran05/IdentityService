package com.dtp.identityservice.mapper;

import com.dtp.identityservice.dto.request.RoleRequest;
import com.dtp.identityservice.dto.response.RoleResponse;
import com.dtp.identityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
    //void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
