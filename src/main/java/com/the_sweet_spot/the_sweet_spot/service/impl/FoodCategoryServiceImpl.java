package com.the_sweet_spot.the_sweet_spot.service.impl;

import com.the_sweet_spot.the_sweet_spot.common.WellKnownStatus;
import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.FoodCategoryRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.FoodCategoryResponseDto;
import com.the_sweet_spot.the_sweet_spot.exception.BadRequestException;
import com.the_sweet_spot.the_sweet_spot.exception.NotFoundException;
import com.the_sweet_spot.the_sweet_spot.model.FoodCategory;
import com.the_sweet_spot.the_sweet_spot.model.Role;
import com.the_sweet_spot.the_sweet_spot.repository.FoodCategoryRepository;
import com.the_sweet_spot.the_sweet_spot.service.AuthenticationService;
import com.the_sweet_spot.the_sweet_spot.service.FoodCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public FoodCategoryResponseDto saveFoodCategory(AuthenticationTicketDto authTicket, FoodCategoryRequestDto request) {
        Boolean isCategoryExist = foodCategoryRepository.existsByFoodCategoryNameAndStatusNot(request.getFoodCategoryName(), WellKnownStatus.DELETED.getValue());

        if (isCategoryExist)
            throw new BadRequestException("Food Category Already Exist!");

        FoodCategory foodCategory = new FoodCategory();

        foodCategory = modelMapper.map(request, FoodCategory.class);

        foodCategory.setAddedBy(authTicket.getUserId());
        foodCategory.setUpdatedBy(authTicket.getUserId());
        foodCategory.setStatus(WellKnownStatus.ACTIVE.getValue());

        FoodCategory savedFoodCategory = foodCategoryRepository.save(foodCategory);

        FoodCategoryResponseDto response = modelMapper.map(savedFoodCategory, FoodCategoryResponseDto.class);

        return response;
    }

    @Override
    public List<FoodCategoryResponseDto> getAllFoodCategory(AuthenticationTicketDto authTicket) {
        String userRole = authTicket.getRole();

        List<FoodCategoryResponseDto> response = null;

        List<FoodCategory> foodCategories = null;

        if (userRole == Role.ADMIN.name()) {
            foodCategories = foodCategoryRepository.findAllByStatusIn(List.of(
                    WellKnownStatus.ACTIVE.getValue(),
                    WellKnownStatus.INACTIVE.getValue()
            ));
        } else if (userRole == Role.USER.name()) {
            foodCategories = foodCategoryRepository.findAllByStatusIn(List.of(
                    WellKnownStatus.ACTIVE.getValue()
            ));
        }
        response = modelMapper.map(foodCategories, new TypeToken<List<FoodCategoryResponseDto>>(){}.getType());

        return response;
    }

    @Override
    public FoodCategoryResponseDto getFoodCategoryById(AuthenticationTicketDto authTicket, Long id) {
        String userRole = authTicket.getRole();

        FoodCategoryResponseDto response = null;

        FoodCategory foodCategory = null;

        if (userRole == Role.ADMIN.name()) {
            foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                    WellKnownStatus.ACTIVE.getValue(),
                    WellKnownStatus.INACTIVE.getValue()
            ));
        } else if (userRole == Role.USER.name()) {
            foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                    WellKnownStatus.ACTIVE.getValue()
            ));
        }

        if(foodCategory == null)
            throw new NotFoundException("Food Category Not Found!");

        response = modelMapper.map(foodCategory, FoodCategoryResponseDto.class);

        return response;
    }

    @Override
    @Transactional
    public FoodCategoryResponseDto updateFoodCategory(AuthenticationTicketDto authTicket, Long id, FoodCategoryRequestDto request) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                WellKnownStatus.ACTIVE.getValue(),
                WellKnownStatus.INACTIVE.getValue()
        ));

        if(foodCategory == null)
            throw new NotFoundException("Food Category Not Found!");

        foodCategory.setFoodCategoryName(request.getFoodCategoryName());
        foodCategory.setFoodCategoryDescription(request.getFoodCategoryDescription());
        foodCategory.setUpdatedBy(authTicket.getUserId());

        FoodCategory updatedFoodCategory = foodCategoryRepository.save(foodCategory);

        FoodCategoryResponseDto response = modelMapper.map(updatedFoodCategory, FoodCategoryResponseDto.class);

        return response;
    }

    @Override
    @Transactional
    public FoodCategoryResponseDto activeInactiveFoodCategory(AuthenticationTicketDto authTicket, Long id, String status) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                WellKnownStatus.ACTIVE.getValue(),
                WellKnownStatus.INACTIVE.getValue()
        ));

        if(foodCategory == null)
            throw new NotFoundException("Food Category Not Found!");

        if(status.toLowerCase().equals("active")) {
            foodCategory.setStatus(WellKnownStatus.ACTIVE.getValue());
        } else if(status.toLowerCase().equals("inactive")) {
            foodCategory.setStatus(WellKnownStatus.INACTIVE.getValue());
        }else {
            throw new BadRequestException("Invalid Status!");
        }

        foodCategory.setUpdatedBy(authTicket.getUserId());

        FoodCategory updatedFoodCategory = foodCategoryRepository.save(foodCategory);

        FoodCategoryResponseDto response = modelMapper.map(updatedFoodCategory, FoodCategoryResponseDto.class);

        return response;
    }

    @Override
    @Transactional
    public void deleteFoodCategory(AuthenticationTicketDto authTicket, Long id) {
        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategoryIdAndStatusIn(id, List.of(
                WellKnownStatus.ACTIVE.getValue(),
                WellKnownStatus.INACTIVE.getValue()
        ));

        if(foodCategory == null)
            throw new NotFoundException("Food Category Not Found!");

        foodCategory.setStatus(WellKnownStatus.DELETED.getValue());
        foodCategory.setUpdatedBy(authTicket.getUserId());

        foodCategoryRepository.save(foodCategory);

    }
}
