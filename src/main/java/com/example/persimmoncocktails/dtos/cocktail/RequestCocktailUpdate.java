package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCocktailUpdate {
    @NotNull
    private Long dishId;
    @NotNull
    private String name;
    private String description;
    private String dishType;
    private Long dishCategoryId;
    @NotNull
    private String receipt;
    @NotNull
    private Boolean isActive;
    private List<String> labels;
    private List<Long> ingredientList;
    private List<Long> kitchenwareIds;
}

