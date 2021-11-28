package com.example.persimmoncocktails.models.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientCategory {
    private Long ingredientCategoryId;
    private String name;
}
