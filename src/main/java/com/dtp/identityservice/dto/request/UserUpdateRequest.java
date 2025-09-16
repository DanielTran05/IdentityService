package com.dtp.identityservice.dto.request;

import com.dtp.identityservice.entity.Role;
import com.dtp.identityservice.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min=8, message = "PASSWORD_INVALID")
    String password;

    @DobConstraint(min = 2, message = "INVALID_DOB")
    LocalDate dob;

    //List<String> roles;
}


