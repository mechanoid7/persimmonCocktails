package com.example.persimmoncocktails.models.Stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockIngredient {
    private Long id;
    private String name;
    private String measureType;
    private int amount;
}
