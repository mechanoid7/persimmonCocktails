package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestIngredientCocktailDto {
    Long ingredientId;
    Long cocktailId;
}
