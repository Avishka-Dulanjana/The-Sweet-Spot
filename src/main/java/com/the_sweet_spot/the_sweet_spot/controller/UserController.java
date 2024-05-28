package com.the_sweet_spot.the_sweet_spot.controller;

import com.the_sweet_spot.the_sweet_spot.common.ApplicationRoute;
import com.the_sweet_spot.the_sweet_spot.common.CommonResponse;
import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserSaveRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.UserUpdateRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.UserResponseDto;
import com.the_sweet_spot.the_sweet_spot.service.AuthenticationService;
import com.the_sweet_spot.the_sweet_spot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.User.Root)
public class UserController {
    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.User.Save)
    public ResponseEntity<CommonResponse> Register(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto) {
        ResponseEntity<CommonResponse> response = null;

        UserResponseDto user = userService.register(userSaveRequestDto);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "User registered!", user),
                HttpStatus.CREATED
        );

        return response;
    }

    @GetMapping(ApplicationRoute.User.GetProfile)
    ResponseEntity<CommonResponse> GetUserProfile (){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        UserResponseDto user = userService.getUserProfile(authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", user),
                HttpStatus.OK
        );
        return response;
    }

    @PutMapping(ApplicationRoute.User.UpdateProfile)
    ResponseEntity<CommonResponse> UpdateUserProfile (@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        UserResponseDto user = userService.updateUserProfile(authTicket.getUserId(), userUpdateRequestDto);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "User profile updated!", user),
                HttpStatus.OK
        );

        return response;
    }

    @DeleteMapping(ApplicationRoute.User.DeleteProfile)
    ResponseEntity<CommonResponse> DeleteUserProfile (){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        userService.deleteUserProfile(authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "User profile deleted!", null),
                HttpStatus.OK
        );

        return response;
    }

    @GetMapping(ApplicationRoute.User.GetAll)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<CommonResponse> GetAllUsers(){
        ResponseEntity<CommonResponse> response = null;

        List<UserResponseDto> users = userService.getAllUsers();

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", users),
                HttpStatus.OK
        );

        return response;
    }
}
