package com.example.persimmoncocktails.dtos.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestStockIngredientSelectDto {
    String name; // search
    String sortBy; // sort
    int amount;
    String measureType;
    Long ingredientCategoryId; // filter
    Boolean sortDirection; // asc/desc
}
