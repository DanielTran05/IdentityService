package com.dtp.identityservice.service;

import com.dtp.identityservice.dto.request.RoleRequest;
import com.dtp.identityservice.dto.response.RoleResponse;
import com.dtp.identityservice.entity.Permission;
import com.dtp.identityservice.mapper.RoleMapper;
import com.dtp.identityservice.repository.PermissionRepository;
import com.dtp.identityservice.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role =  roleMapper.toRole(request);

        //if(!request.getPermission().isEmpty()) {
            List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
            role.setPermissions(new HashSet<>(permissions));
        //}

         roleRepository.save(role);

         return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete (String name){
        roleRepository.deleteById(name);
    }
}
