package com.the_sweet_spot.the_sweet_spot.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {
    @NotNull(message = "Product is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private int quantity;
}
