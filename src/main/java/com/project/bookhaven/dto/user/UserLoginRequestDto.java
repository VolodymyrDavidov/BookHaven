package com.project.bookhaven.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequestDto(@Email String email, @NotEmpty String password) {
}
