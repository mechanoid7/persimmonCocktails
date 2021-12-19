package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
    private Long photoId;

    public List<Long> getUniqueIngredientIds() {
        if(ingredientList == null) return null;
        return ingredientList
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Long> getUniqueKitchenwareIds() {
        if(kitchenwareIds == null) return null;
        return kitchenwareIds
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }
}

