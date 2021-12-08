package com.example.persimmoncocktails.dtos.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class IngredientNameDto {
    Long ingredientId;

    String name;
}
