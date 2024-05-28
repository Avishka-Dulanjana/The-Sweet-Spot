package com.the_sweet_spot.the_sweet_spot.controller;

import com.the_sweet_spot.the_sweet_spot.common.ApplicationRoute;
import com.the_sweet_spot.the_sweet_spot.common.CommonResponse;
import com.the_sweet_spot.the_sweet_spot.dto.request.RefreshTokenRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserLoginRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserSaveRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.RefreshTokenResponseDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserLoginResponseDto;
import com.the_sweet_spot.the_sweet_spot.service.AuthenticationService;
import com.the_sweet_spot.the_sweet_spot.service.UserService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.Authentication.Root)
public class AuthenticationController {
    private final  AuthenticationService authenticationService;
    @PostMapping(ApplicationRoute.Authentication.Login)
    public ResponseEntity<CommonResponse> UserLogin(@RequestBody @Valid UserLoginRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        UserLoginResponseDto userLoginResponseDto = authenticationService.UserLogin(request);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Login Successful!", userLoginResponseDto),
                HttpStatus.OK
        );

        return  response;
    }

    @PostMapping(ApplicationRoute.Authentication.RefreshToken)
    public ResponseEntity<CommonResponse> RefreshToken(@RequestBody RefreshTokenRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        UserLoginResponseDto responseDto = authenticationService.GetRefreshToken(request);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Token refresh successful!", responseDto),
                HttpStatus.OK
        );

        return  response;
    }
}
