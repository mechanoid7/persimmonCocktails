package com.example.persimmoncocktails.dtos.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestStockIngredientSelectDto {
    private Long personId;
    String name; // search
    String sortBy; // sort
    Long ingredientCategoryId;
    private String categoryName;// filter
    Boolean sortDirection; // asc/desc
}
