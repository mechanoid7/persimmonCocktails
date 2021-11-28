package com.example.persimmoncocktails.models.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CocktailCategory {
    Long categoryId;
    String categoryName;
}
