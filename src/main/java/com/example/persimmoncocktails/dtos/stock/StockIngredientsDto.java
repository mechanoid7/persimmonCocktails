package com.example.persimmoncocktails.dtos.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockIngredientsDto {
    private Long id;
    private String name;
    private String measureType;
    private int amount;
    private Long photoId;
}
