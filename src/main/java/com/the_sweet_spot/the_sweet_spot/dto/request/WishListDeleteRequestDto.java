package com.the_sweet_spot.the_sweet_spot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WishListDeleteRequestDto {
    List<Long> productIds;
}
