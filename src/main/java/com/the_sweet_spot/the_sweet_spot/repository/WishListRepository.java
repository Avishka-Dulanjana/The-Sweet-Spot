package com.the_sweet_spot.the_sweet_spot.repository;

import com.the_sweet_spot.the_sweet_spot.model.User;
import com.the_sweet_spot.the_sweet_spot.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findAllByUserAndStatusIn(User user, List<Integer> status);

    WishList findByWishListIdAndStatusIn(Long wishListId, List<Integer> status);
}
