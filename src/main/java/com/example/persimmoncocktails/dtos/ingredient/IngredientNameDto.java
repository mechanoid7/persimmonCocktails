package com.example.persimmoncocktails.dtos.ingredient;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class IngredientNameDto {
    Long ingredientId;

    String name;

    ImageResponseDto image;
}
