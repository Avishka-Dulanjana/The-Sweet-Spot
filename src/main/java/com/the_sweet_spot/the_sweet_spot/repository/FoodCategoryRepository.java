package com.the_sweet_spot.the_sweet_spot.repository;

import com.the_sweet_spot.the_sweet_spot.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
    Boolean existsByFoodCategoryNameAndStatusNot(String foodCategoryName, Integer status);

    List<FoodCategory> findAllByStatusIn(List<Integer> status);

    FoodCategory findByFoodCategoryIdAndStatusIn(Long id, List<Integer> status);

    Boolean existsByFoodCategoryIdAndStatusIn(Long id, List<Integer> status);
}
