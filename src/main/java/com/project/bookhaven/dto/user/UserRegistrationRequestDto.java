package com.project.bookhaven.dto.user;

import com.project.bookhaven.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@FieldMatch(message = "Password fields must match")
public class UserRegistrationRequestDto {
    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 4, max = 8)
    private String password;
    private String repeatPassword;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private String shippingAddress;
}
