package com.the_sweet_spot.the_sweet_spot.dto.request;

import com.the_sweet_spot.the_sweet_spot.model.FoodCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodTypeRequestDto {
    private Long foodTypeId;

    @NotEmpty(message = "Food Type Name is required")
    private String foodTypeName;

    @Size(max = 500, message = "Description should not be more than 500 characters")
    private String foodTypeDescription;

    @NotNull(message = "Food Category is required")
    private Long foodCategoryId;
}
