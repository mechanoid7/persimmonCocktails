package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestKitchenwareCocktailDto {
    Long kitchenwareId;
    Long cocktailId;
}
