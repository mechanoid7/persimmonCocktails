package com.example.persimmoncocktails.models.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientWithCategory {
    private Long ingredientId;
    private String name;
    private Long photoId;
    private IngredientCategory category;
    private boolean isActive;

    public Ingredient toIngredient() {
        return new Ingredient(
                ingredientId,
                name,
                category == null ? null : category.getIngredientCategoryId(),
                photoId,
                isActive
        );
    }
}
