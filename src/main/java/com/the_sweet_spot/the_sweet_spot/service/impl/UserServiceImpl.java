package com.the_sweet_spot.the_sweet_spot.service.impl;

import com.the_sweet_spot.the_sweet_spot.common.WellKnownStatus;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserSaveRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserUpdateRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.FoodTypeResponseDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserResponseDto;
import com.the_sweet_spot.the_sweet_spot.exception.BadRequestException;
import com.the_sweet_spot.the_sweet_spot.exception.NotFoundException;
import com.the_sweet_spot.the_sweet_spot.model.Role;
import com.the_sweet_spot.the_sweet_spot.model.User;
import com.the_sweet_spot.the_sweet_spot.repository.UserRepository;
import com.the_sweet_spot.the_sweet_spot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final EmailServiceImpl emailService;

    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmailAndStatus(username, WellKnownStatus.ACTIVE.getValue())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
            }
        };
    }

    @Override
    public UserResponseDto register(UserSaveRequestDto userSaveRequestDto) {
        // Check if email already exist
        Boolean isEmailExist = userRepository.existsUserByEmail(userSaveRequestDto.getEmail());

        if (isEmailExist)
            throw new BadRequestException("Email Already Exist!");

        // Check if role is valid
        try {
            Role role = Role.valueOf(userSaveRequestDto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid Role!");
        }
        User user = modelMapper.map(userSaveRequestDto, User.class);

        String passwordHash = passwordEncoder.encode(userSaveRequestDto.getPassword());

        user.setPassword(passwordHash);
        user.setStatus(WellKnownStatus.ACTIVE.getValue());
        user.setIsVerified(false);

        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = modelMapper.map(savedUser, UserResponseDto.class);

        // Send Email
        HashMap<String, Object> templateData = new HashMap<>();
        templateData.put("name", userResponseDto.getFirstName());

        emailService.sendHtmlTemplateEmail(userResponseDto.getEmail(), "Welcome to Red Bakery", "RegisterEmailTemplate.ftl", templateData);


        return userResponseDto;

    }

    @Override
    public UserResponseDto getUserProfile(Long userId) {
        UserResponseDto userResponseDto = null;
        User user = userRepository.getReferenceById(userId);

        if (user == null || user.getStatus() == WellKnownStatus.DELETED.getValue())
            throw new NotFoundException("User not found!");
        else {
            userResponseDto = modelMapper.map(user, UserResponseDto.class);
        }
        return userResponseDto;
    }

    @Override
    public UserResponseDto updateUserProfile(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        UserResponseDto userResponseDto = null;
        User user = userRepository.getReferenceById(userId);

        if (user != null && user.getStatus() != WellKnownStatus.DELETED.getValue()) {
            user.setFirstName(userUpdateRequestDto.getFirstName());
            user.setLastName(userUpdateRequestDto.getLastName());
            user.setEmail(userUpdateRequestDto.getEmail());
            user.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
            user.setProfileImage(userUpdateRequestDto.getProfileImage());

            try {
                Role role = Role.valueOf(userUpdateRequestDto.getRole().toUpperCase());
                user.setRole(role);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid Role!");
            }

            User savedUser = userRepository.save(user);

            userResponseDto = modelMapper.map(savedUser, UserResponseDto.class);

        } else {
            throw new NotFoundException("User not found!");
        }
        return userResponseDto;
    }

    @Override
    public void deleteUserProfile(Long userId) {
        User user = userRepository.getReferenceById(userId);

        if (user != null && user.getStatus() != WellKnownStatus.DELETED.getValue()) {
            user.setStatus(WellKnownStatus.DELETED.getValue());

            userRepository.save(user);
        } else {
            throw new NotFoundException("User not found!");
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> users = null;

        List<User> userList = userRepository.findAllByStatus(WellKnownStatus.ACTIVE.getValue());
        users = modelMapper.map(userList, new TypeToken<List<UserResponseDto>>() {
        }.getType());

        return users;
    }
}
