package com.dtp.identityservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})                                //su dung cho cai gi
@Retention(RetentionPolicy.RUNTIME)                         //thoi diem xu ly
@Constraint(validatedBy = {DobValidator.class})             //chi dinh Validator xu ly
public @interface DobConstraint {
    //3 basic attributes for validation annotation
    String message() default "Invalid DOB";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    //customize
    int min();
}
