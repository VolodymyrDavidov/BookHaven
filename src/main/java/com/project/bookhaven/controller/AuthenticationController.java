package com.project.bookhaven.controller;

import com.project.bookhaven.dto.user.UserLoginRequestDto;
import com.project.bookhaven.dto.user.UserLoginResponseDto;
import com.project.bookhaven.dto.user.UserRegistrationRequestDto;
import com.project.bookhaven.dto.user.UserResponseDto;
import com.project.bookhaven.exception.RegistrationException;
import com.project.bookhaven.security.AuthenticationService;
import com.project.bookhaven.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    @Operation(summary = "Login user", description = "Endpoint to login user")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping(value = "/registration")
    @Operation(summary = "Authenticate user", description = "Endpoint for authentication")
    public UserResponseDto registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
