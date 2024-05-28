package com.the_sweet_spot.the_sweet_spot.repository;

import com.the_sweet_spot.the_sweet_spot.model.FoodCategory;
import com.the_sweet_spot.the_sweet_spot.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
    Boolean existsByFoodTypeNameAndFoodCategoryAndStatusNot(String foodTypeName, FoodCategory foodCategory, Integer status);

    List<FoodType> findAllByStatusIn(List<Integer> statusList);

    FoodType findByFoodTypeIdAndStatusIn(Long foodTypeId, List<Integer> statusList);

    List<FoodType> findAllByFoodCategoryAndStatusIn(FoodCategory foodCategory, List<Integer> statusList);
}
