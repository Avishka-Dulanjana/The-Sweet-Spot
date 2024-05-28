package com.the_sweet_spot.the_sweet_spot.repository;

import com.the_sweet_spot.the_sweet_spot.model.Cart;
import com.the_sweet_spot.the_sweet_spot.model.CartDetail;
import com.the_sweet_spot.the_sweet_spot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserAndStatusIn(User user, List<Integer> status);
}
