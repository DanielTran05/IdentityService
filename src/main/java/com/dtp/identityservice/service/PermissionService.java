package com.dtp.identityservice.service;

import com.dtp.identityservice.dto.request.PermissionRequest;
import com.dtp.identityservice.dto.response.PermissionResponse;
import com.dtp.identityservice.entity.Permission;
import com.dtp.identityservice.mapper.PermissionMapper;
import com.dtp.identityservice.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){
         var permission =  permissionMapper.toPermission(request);
         permissionRepository.save(permission);

         return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete (String name){
        permissionRepository.deleteById(name);
    }



}
