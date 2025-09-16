package com.dtp.identityservice.service;

import com.dtp.identityservice.dto.request.UserCreationRequest;
import com.dtp.identityservice.dto.request.UserUpdateRequest;
import com.dtp.identityservice.dto.response.UserResponse;
import com.dtp.identityservice.entity.User;
import com.dtp.identityservice.exception.AppException;
import com.dtp.identityservice.exception.ErrorCode;
import com.dtp.identityservice.mapper.UserMapper;
import com.dtp.identityservice.repository.RoleRepository;
import com.dtp.identityservice.repository.UserRepository;
import enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        //user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")               //@PerAuthorize("hasAuthority('APPROVE_POST')")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String userId){
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED)));
    }

    public UserResponse updateUser(String userId, @NotNull UserUpdateRequest request){
        User u = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        userMapper.updateUser(u, request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        u.setPassword(passwordEncoder.encode(request.getPassword()));

        /*var roles = roleRepository.findAllById(request.getRoles()); //list string to list role
        u.setRoles(new HashSet<>(roles));                           //list role to set role
*/
        return userMapper.toUserResponse(userRepository.save(u));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();           //current user
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new RuntimeException("Auth is null");

        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }


}
