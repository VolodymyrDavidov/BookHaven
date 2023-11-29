package com.project.bookhaven.service.user;

import com.project.bookhaven.dto.user.UserRegistrationRequestDto;
import com.project.bookhaven.dto.user.UserResponseDto;
import com.project.bookhaven.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
