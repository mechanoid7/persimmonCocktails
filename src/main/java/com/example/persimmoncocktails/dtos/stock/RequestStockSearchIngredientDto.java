package com.example.persimmoncocktails.dtos.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStockSearchIngredientDto {
    private Long personId;
    private Long ingredientId;
    private String name;
    private Long photoId;
    private Long ingredientCategoryId;
    private String categoryName;
    private int amount;
    private String measureType;
}
