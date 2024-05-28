package com.the_sweet_spot.the_sweet_spot.service;

import com.the_sweet_spot.the_sweet_spot.common.CommonPaginatedResponse;
import com.the_sweet_spot.the_sweet_spot.dto.AuthenticationTicketDto;
import com.the_sweet_spot.the_sweet_spot.dto.request.ProductRequestDto;
import com.the_sweet_spot.the_sweet_spot.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto saveProduct(AuthenticationTicketDto authTicket, ProductRequestDto request);

    List<ProductResponseDto> getAllProducts(AuthenticationTicketDto authTicket);

    ProductResponseDto getProductById(AuthenticationTicketDto authTicket, Long id);

    void deleteProductById(AuthenticationTicketDto authTicket, Long id);

    ProductResponseDto activeInactiveProduct(AuthenticationTicketDto authTicket, Long id, String status);

    List<ProductResponseDto> getProductByFoodType(AuthenticationTicketDto authTicket, Long id);

    List<ProductResponseDto> searchProduct(AuthenticationTicketDto authTicket, String name);

    ProductResponseDto updateProduct(AuthenticationTicketDto authTicket, Long id, ProductRequestDto request);
}
