package com.dtp.identityservice.dto.request;

import com.dtp.identityservice.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;
    @Size(min=5, message = "PASSWORD_INVALID")
    String password;

    @DobConstraint(min = 2, message = "INVALID_DOB")
    LocalDate dob;
}
