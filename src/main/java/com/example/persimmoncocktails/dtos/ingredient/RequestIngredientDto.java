package com.example.persimmoncocktails.dtos.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestIngredientDto {
    private String name;
    private Long ingredientCategoryId;
    private Long photoId;
}
