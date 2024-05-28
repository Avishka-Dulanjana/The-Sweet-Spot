package com.the_sweet_spot.the_sweet_spot.controller;

import com.the_sweet_spot.the_sweet_spot.common.ApplicationRoute;
import com.the_sweet_spot.the_sweet_spot.common.CommonResponse;
import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.CartRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.GetCartResponseDto;
import com.the_sweet_spot.the_sweet_spot.service.AuthenticationService;
import com.the_sweet_spot.the_sweet_spot.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.Cart.Root)
public class CartController {
    private final CartService cartService;

    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.Cart.AddToCart)
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CommonResponse> AddToCart(@RequestBody @Valid CartRequestDto cartRequestDto) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        cartService.addToCart(authTicket, cartRequestDto);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Added To Cart!", null),
                HttpStatus.CREATED
        );

    }

    @PutMapping(ApplicationRoute.Cart.UpdateCart)
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CommonResponse> UpdateCart(@RequestBody @Valid List<CartRequestDto> request) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        cartService.updateCart(authTicket, request);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Cart Updated!", null),
                HttpStatus.OK
        );

    }

    @DeleteMapping(ApplicationRoute.Cart.RemoveFromCart)
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CommonResponse> RemoveFromCart(@PathVariable Long id) {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        cartService.removeFromCart(authTicket, id);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Product Removed From Cart!", null),
                HttpStatus.OK
        );

    }

    @GetMapping(ApplicationRoute.Cart.GetCart)
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<CommonResponse> GetCart() {
        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        GetCartResponseDto cartDetails = cartService.getCart(authTicket);

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", cartDetails),
                HttpStatus.OK
        );

    }
}
