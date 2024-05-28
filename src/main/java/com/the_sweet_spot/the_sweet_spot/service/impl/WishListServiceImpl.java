package com.the_sweet_spot.the_sweet_spot.service.impl;

import com.the_sweet_spot.the_sweet_spot.common.WellKnownStatus;
import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.WishListResponseDto;
import com.the_sweet_spot.the_sweet_spot.exception.BadRequestException;
import com.the_sweet_spot.the_sweet_spot.model.Product;
import com.the_sweet_spot.the_sweet_spot.model.WishList;
import com.the_sweet_spot.the_sweet_spot.repository.ProductRepository;
import com.the_sweet_spot.the_sweet_spot.repository.UserRepository;
import com.the_sweet_spot.the_sweet_spot.repository.WishListRepository;
import com.the_sweet_spot.the_sweet_spot.service.WishListService;
import com.the_sweet_spot.the_sweet_spot.util.CustomMapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final ProductRepository productRepository;

    private final WishListRepository wishListRepository;

    private final UserRepository userRepository;

    @Override
    public void addProductToWishList(AuthenticationTicketDto authTicket, Long id) {
        Product product = productRepository.findByProductIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));

        if (product == null)
            throw new BadRequestException("Invalid Product Id!");

        List<WishList> wishListItemCheck = wishListRepository.findAllByUserAndStatusIn(authTicket.getUser(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if (!wishListItemCheck.isEmpty())
            throw new BadRequestException("Product already exists in wishlist!");

        WishList wishList = new WishList();

        wishList.setProduct(product);
        wishList.setUser(authTicket.getUser());
        wishList.setStatus(WellKnownStatus.ACTIVE.getValue());

        wishListRepository.save(wishList);
    }

    @Override
    @Transactional
    public List<WishListResponseDto> getWishList(AuthenticationTicketDto authTicket) {
        List<WishListResponseDto> response = new ArrayList<>();
        List<WishList> wishList = wishListRepository.findAllByUserAndStatusIn(authTicket.getUser(), List.of(WellKnownStatus.ACTIVE.getValue()));

        if(!wishList.isEmpty()){
            wishList.forEach(wishListItem -> {
                if(wishListItem.getProduct().getStatus() == WellKnownStatus.ACTIVE.getValue()){
                    WishListResponseDto wishListResponseDto = mapProductToWishListResponseDto(wishListItem);
                    response.add(wishListResponseDto);
                }else {
                    wishListRepository.delete(wishListItem);
                }
            });
        }

        return response;
    }

    @Override
    public void removeFromWishList(AuthenticationTicketDto authTicket, Long id) {
        WishList wishList = wishListRepository.findByWishListIdAndStatusIn(id, List.of(WellKnownStatus.ACTIVE.getValue()));

        if (wishList == null)
            throw new BadRequestException("Invalid Wishlist Id!");

        wishListRepository.delete(wishList);
    }

    private static WishListResponseDto mapProductToWishListResponseDto(WishList wishList){
        Date currentDate = new Date();
        WishListResponseDto wishListResponseDto = new WishListResponseDto();
        Product product = wishList.getProduct();

        wishListResponseDto.setProductId(product.getProductId());
        wishListResponseDto.setProductName(product.getProductName());
        wishListResponseDto.setProductDescription(product.getProductDescription());
        wishListResponseDto.setProductImage(product.getProductImage());
        wishListResponseDto.setFoodTypeId(product.getFoodType().getFoodTypeId());
        wishListResponseDto.setFoodTypeName(product.getFoodType().getFoodTypeName());
        wishListResponseDto.setStatus(product.getStatus());
        wishListResponseDto.setIsDiscounted(false);
        wishListResponseDto.setWishListId(wishList.getWishListId());
        wishListResponseDto.setAddedDate(wishList.getCreatedAt());

        if (product.getIsDiscounted()) {
            if (currentDate.after(product.getDiscount().getStartDate()) && currentDate.before(product.getDiscount().getEndDate())) {
                wishListResponseDto.setIsDiscounted(product.getIsDiscounted());
                wishListResponseDto.setDiscountPercentage(product.getDiscount().getDiscountPercentage());
                wishListResponseDto.setProductPrice(product.getProductPrice());
                wishListResponseDto.setStartDate(product.getDiscount().getStartDate());
                wishListResponseDto.setEndDate(product.getDiscount().getEndDate());
                wishListResponseDto.setNewPrice(product.getDiscount().getNewPrice());
                wishListResponseDto.setDiscountPrice(product.getDiscount().getDiscountPrice());
            } else {
                wishListResponseDto.setProductPrice(product.getProductPrice());
                wishListResponseDto.setDiscountPercentage(null);
                wishListResponseDto.setNewPrice(null);
                wishListResponseDto.setDiscountPrice(null);
                wishListResponseDto.setStartDate(null);
                wishListResponseDto.setEndDate(null);
            }
        } else {
            wishListResponseDto.setProductPrice(product.getProductPrice());
            wishListResponseDto.setDiscountPercentage(null);
            wishListResponseDto.setNewPrice(null);
            wishListResponseDto.setDiscountPrice(null);
            wishListResponseDto.setStartDate(null);
            wishListResponseDto.setEndDate(null);
        }


        return wishListResponseDto;
    }
}

