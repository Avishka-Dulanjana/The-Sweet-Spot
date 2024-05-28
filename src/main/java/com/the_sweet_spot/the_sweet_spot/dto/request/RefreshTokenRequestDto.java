package com.the_sweet_spot.the_sweet_spot.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshTokenRequestDto {
    @NotEmpty(message = "Token is required")
    private  String token;
}
