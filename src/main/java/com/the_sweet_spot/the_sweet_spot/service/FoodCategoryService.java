package com.the_sweet_spot.the_sweet_spot.service;

import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.FoodCategoryRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.FoodCategoryResponseDto;

import java.util.List;

public interface FoodCategoryService {
    FoodCategoryResponseDto saveFoodCategory(AuthenticationTicketDto authTicket, FoodCategoryRequestDto request);

    List<FoodCategoryResponseDto> getAllFoodCategory(AuthenticationTicketDto authTicket);

    FoodCategoryResponseDto getFoodCategoryById(AuthenticationTicketDto authTicket, Long id);

    FoodCategoryResponseDto updateFoodCategory(AuthenticationTicketDto authTicket, Long id, FoodCategoryRequestDto request);

    FoodCategoryResponseDto activeInactiveFoodCategory(AuthenticationTicketDto authTicket, Long id, String status);

    void deleteFoodCategory(AuthenticationTicketDto authTicket, Long id);
}
