package com.the_sweet_spot.the_sweet_spot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshTokenResponseDto {
    private String token;
    private String accessToken;
}
