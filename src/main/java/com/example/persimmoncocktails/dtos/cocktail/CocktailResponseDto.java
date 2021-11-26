package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CocktailResponseDto {
    private Long dishId;
    private String name;
    private String description;
    private String dishType;
    private String dishCategoryName;
    private Long dishCategoryId;
    private String label;
    private String receipt;
    private Long likes;
    private Boolean isActive;
}

