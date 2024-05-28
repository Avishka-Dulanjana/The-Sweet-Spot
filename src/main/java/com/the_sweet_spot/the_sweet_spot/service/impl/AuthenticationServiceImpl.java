package com.the_sweet_spot.the_sweet_spot.service.impl;

import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.RefreshTokenRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserLoginRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserSaveRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.RefreshTokenResponseDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserLoginResponseDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserResponseDto;
import com.the_sweet_spot.the_sweet_spot.exception.BadRequestException;
import com.the_sweet_spot.the_sweet_spot.model.User;
import com.the_sweet_spot.the_sweet_spot.repository.UserRepository;
import com.the_sweet_spot.the_sweet_spot.service.AuthenticationService;
import com.the_sweet_spot.the_sweet_spot.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Override
    public UserLoginResponseDto UserLogin(UserLoginRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            throw new BadRequestException("Invalid Email or Password!");
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("Invalid Email or Password!"));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshT(new HashMap<>(), user);

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        UserLoginResponseDto response = new UserLoginResponseDto(
                token, refreshToken, userResponseDto
        );

        return response;
    }

    @Override
    public UserLoginResponseDto GetRefreshToken(RefreshTokenRequestDto request) {
        try{
            String email = jwtService.extractUserName(request.getToken());

            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BadRequestException("Invalid Token!")
            );

            if (jwtService.isTokenValid(request.getToken(), user)) {
                String token = jwtService.generateToken(user);

                UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

                UserLoginResponseDto response = new UserLoginResponseDto(
                        token,
                        request.getToken(),
                        userResponseDto
                );

                return response;
            } else {
                throw new BadRequestException("Invalid Token!");
            }
        }catch (Exception e){
            throw new BadRequestException("Invalid Token!");
        }
    }

    @Override
    public AuthenticationTicketDto AuthenticationTicket() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User user = userRepository.findByEmail(userDetails.getUsername()).get();

            AuthenticationTicketDto authTicket = modelMapper.map(user, AuthenticationTicketDto.class);

            authTicket.setUser(user);

            return authTicket;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }


}
