package com.project.bookhaven.validation;

import com.project.bookhaven.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class FieldsValidator implements ConstraintValidator<FieldMatch, Object> {
    @Override
    public void initialize(FieldMatch constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserRegistrationRequestDto user = (UserRegistrationRequestDto) value;
        return user.getPassword().equals(user.getRepeatPassword());
    }
}
