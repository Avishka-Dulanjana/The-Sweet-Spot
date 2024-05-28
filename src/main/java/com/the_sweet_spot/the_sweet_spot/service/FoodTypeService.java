package com.the_sweet_spot.the_sweet_spot.service;

import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.FoodTypeRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.FoodTypeResponseDto;

import java.util.List;

public interface FoodTypeService {
    FoodTypeResponseDto saveFoodType(AuthenticationTicketDto authTicket, FoodTypeRequestDto request);

    List<FoodTypeResponseDto> getAllFoodType(AuthenticationTicketDto authTicket);

    FoodTypeResponseDto getFoodTypeById(AuthenticationTicketDto authTicket, Long id);

    FoodTypeResponseDto updateFoodType(AuthenticationTicketDto authTicket, Long id, FoodTypeRequestDto request);

    FoodTypeResponseDto activeInactiveFoodType(AuthenticationTicketDto authTicket, Long id, String status);

    FoodTypeResponseDto deleteFoodType(AuthenticationTicketDto authTicket, Long id);

    List<FoodTypeResponseDto> getFoodTypeByCategory(AuthenticationTicketDto authTicket, Long id);
}
