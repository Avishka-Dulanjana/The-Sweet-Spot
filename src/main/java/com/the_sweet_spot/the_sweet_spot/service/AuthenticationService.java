package com.the_sweet_spot.the_sweet_spot.service;

import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.RefreshTokenRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserLoginRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserSaveRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.RefreshTokenResponseDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserLoginResponseDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto UserLogin(UserLoginRequestDto request);

    UserLoginResponseDto GetRefreshToken(RefreshTokenRequestDto request);

    AuthenticationTicketDto AuthenticationTicket();

}
