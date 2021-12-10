package com.example.persimmoncocktails.dtos.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAddStockIngredientDto {
    private Long ingredientId;
    private String measureType;
    private int amount;
}
