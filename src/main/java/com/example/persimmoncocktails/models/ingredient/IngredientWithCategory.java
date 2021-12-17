package com.example.persimmoncocktails.models.ingredient;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
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
    private ImageResponseDto image;
    private Long photoId; // for backward compatibility
    private IngredientCategory category;
    private boolean isActive;

    public Ingredient toIngredient() {
        return new Ingredient(
                ingredientId,
                name,
                category == null ? null : category.getIngredientCategoryId(),
                image == null ? null : image.getImageId(),
                isActive
        );
    }
}
