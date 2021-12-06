package com.example.persimmoncocktails.dtos.cocktail;

import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
//@NoArgsConstructor
public class FullCocktailDto {
    private Long dishId;
    private String name;
    private String description;
    private String dishType;
    private String dishCategoryName;
    private Long dishCategoryId;
    private List<String> labels;
    private String receipt;
    private Long likes;
    private Boolean isActive;
    private List<KitchenwareWithCategory> kitchenwareList;
    private List<IngredientWithCategory> ingredientList;

    public FullCocktailDto(BasicCocktailDto cocktail,
                           List<KitchenwareWithCategory> kitchenwareList,
                           List<IngredientWithCategory> ingredientList) {
        dishId = cocktail.getDishId();
        name = cocktail.getName();
        description = cocktail.getDescription();
        dishType = cocktail.getDishType();
        dishCategoryId = cocktail.getDishCategoryId();
        dishCategoryName = cocktail.getDishCategoryName();
        labels = cocktail.getLabels();
        receipt = cocktail.getReceipt();
        likes = cocktail.getLikes();
        isActive = cocktail.getIsActive();
        this.kitchenwareList = kitchenwareList;
        this.ingredientList = ingredientList;
    }
}
