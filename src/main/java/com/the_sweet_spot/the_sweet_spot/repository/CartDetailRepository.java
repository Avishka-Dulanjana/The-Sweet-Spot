package com.the_sweet_spot.the_sweet_spot.repository;

import com.the_sweet_spot.the_sweet_spot.model.Cart;
import com.the_sweet_spot.the_sweet_spot.model.CartDetail;
import com.the_sweet_spot.the_sweet_spot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findAllByCartAndProductAndStatusIn(Cart cart, Product product, List<Integer> status);
    List<CartDetail> findAllByCartAndStatusIn(Cart cart, List<Integer> status);
    CartDetail findByCartDetailsIdAndStatusIn(Long cartDetailId, List<Integer> status);
}
