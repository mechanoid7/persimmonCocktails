package com.example.persimmoncocktails.models.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private Long ingredientId;
    private String name;
    private Long ingredientCategoryId;
    private Long photoId;
    private boolean isActive;
}
