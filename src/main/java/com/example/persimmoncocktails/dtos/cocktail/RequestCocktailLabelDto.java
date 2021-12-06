package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestCocktailLabelDto {
    Long dishId;
    String label;
}
