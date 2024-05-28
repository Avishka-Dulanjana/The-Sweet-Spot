package com.the_sweet_spot.the_sweet_spot.repository;

import com.the_sweet_spot.the_sweet_spot.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
