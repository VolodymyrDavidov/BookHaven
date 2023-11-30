package com.project.bookhaven.service.user;

import com.project.bookhaven.dto.user.UserRegistrationRequestDto;
import com.project.bookhaven.dto.user.UserResponseDto;
import com.project.bookhaven.exception.RegistrationException;
import com.project.bookhaven.mapper.UserMapper;
import com.project.bookhaven.model.User;
import com.project.bookhaven.repository.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(@Valid UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findUserByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("This email is already in use");
        }
        User user = new User();
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setPassword(userRegistrationRequestDto.getPassword());
        user.setFirstName(userRegistrationRequestDto.getFirstName());
        user.setLastName(userRegistrationRequestDto.getLastName());
        user.setShippingAddress(userRegistrationRequestDto.getShippingAddress());
        return userMapper.toDto(userRepository.save(user));
    }
}
