package com.the_sweet_spot.the_sweet_spot.dto;

import com.the_sweet_spot.the_sweet_spot.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationTicketDto {
    private Long userId;
    private String email;
    private String role;
    private User user;
}
