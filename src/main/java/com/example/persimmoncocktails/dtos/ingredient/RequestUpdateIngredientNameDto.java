package com.example.persimmoncocktails.dtos.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateIngredientNameDto {
    Long ingredientId;
    String name;
}
