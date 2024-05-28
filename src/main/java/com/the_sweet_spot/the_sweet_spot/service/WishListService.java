package com.the_sweet_spot.the_sweet_spot.service;

import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.WishListResponseDto;

import java.util.List;

public interface WishListService {
    void addProductToWishList(AuthenticationTicketDto authTicket, Long id);

    List<WishListResponseDto> getWishList(AuthenticationTicketDto authTicket);

    void removeFromWishList(AuthenticationTicketDto authTicket, Long id);
}
