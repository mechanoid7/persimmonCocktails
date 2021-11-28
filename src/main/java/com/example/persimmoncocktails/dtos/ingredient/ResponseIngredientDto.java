package com.example.persimmoncocktails.dtos.ingredient;

import com.example.persimmoncocktails.models.ingredient.IngredientCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseIngredientDto {
    private Long ingredientId;
    private String name;
    private IngredientCategory category;
    private Long photoId;

    public static ResponseIngredientDto toDto(IngredientWithCategory ingredient) {
        return new ResponseIngredientDto(
                ingredient.getIngredientId(),
                ingredient.getName(),
                ingredient.getCategory(),
                ingredient.getPhotoId()
        );
    }
}
