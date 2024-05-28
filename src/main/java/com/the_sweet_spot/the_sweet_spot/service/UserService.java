package com.the_sweet_spot.the_sweet_spot.service;

import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserSaveRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserUpdateRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailService();

    public UserResponseDto register(UserSaveRequestDto userSaveRequestDto);

    UserResponseDto getUserProfile(Long userId);

    UserResponseDto updateUserProfile(Long userId, UserUpdateRequestDto userUpdateRequestDto);

    void deleteUserProfile(Long userId);

    List<UserResponseDto> getAllUsers();
}
