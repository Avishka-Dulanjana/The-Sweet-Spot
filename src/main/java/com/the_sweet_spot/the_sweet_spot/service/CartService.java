package com.the_sweet_spot.the_sweet_spot.service;

import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.CartRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.GetCartResponseDto;

import java.util.List;

public interface CartService {
    void addToCart(AuthenticationTicketDto authTicket, CartRequestDto cartRequestDto);

    void updateCart(AuthenticationTicketDto authTicket, List<CartRequestDto> request);

    void removeFromCart(AuthenticationTicketDto authTicket, Long cartDetailId);

    GetCartResponseDto getCart(AuthenticationTicketDto authTicket);
}
