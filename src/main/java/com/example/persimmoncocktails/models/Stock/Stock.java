package com.example.persimmoncocktails.models.Stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private Long personId;
    private Long stockId;
    private List<Ingredient> ingredients;

}