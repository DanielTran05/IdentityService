package com.dtp.identityservice.Service;

import com.dtp.identityservice.dto.request.UserCreationRequest;
import com.dtp.identityservice.dto.response.UserResponse;
import com.dtp.identityservice.entity.User;
import com.dtp.identityservice.exception.AppException;
import com.dtp.identityservice.repository.UserRepository;
import com.dtp.identityservice.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse response;
    private LocalDate dob;
    private User user;

    @BeforeEach
    void innitData(){
        dob = LocalDate.of(2005, 12, 2);

        request = UserCreationRequest.builder()
                .username("phuThang9")
                .password("241188")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id("30f36d66a9d7")
                .username("phuThang9")
                .dob(dob)
                .build();

        user = User.builder()
                .id("30f36d66a9d7")
                .username("phuThang9")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        /*//given
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        //when
        var response = userService.createUser(request);

        //then
        Assertions.assertThat(response.getId()).isEqualTo("30f36d66a9d7");
        Assertions.assertThat(response.getUsername()).isEqualTo("phuThang9");*/
        //given
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        //when
        var response = userService.createUser(request);

        //then
        Assertions.assertThat(response.getId()).isEqualTo("30f36d66a9d7");
        Assertions.assertThat(response.getUsername()).isEqualTo("phuThang9");
    }

    @Test
    void createUser_userExisted_success(){
        //given
        //simulate behavioral mock
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        //when (client calls)
        var exeption = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
                () -> userService.createUser(request));

        //then
        Assertions.assertThat(exeption.getErrorCode().getCode())
                .isEqualTo(1002);
    }


}
