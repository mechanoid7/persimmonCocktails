package com.example.persimmoncocktails.dao;


import com.example.persimmoncocktails.models.ingredient.Ingredient;
import com.example.persimmoncocktails.models.ingredient.IngredientCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;

import java.util.List;

public interface IngredientDao {
    void create(Ingredient ingredient);

    void update(Ingredient ingredient);

    void delete(Long ingredientId);

    boolean existsById(Long ingredientId);

    IngredientWithCategory read(Long ingredientId);

    IngredientWithCategory readByName(String name);

    boolean existsCategoryById(Long ingredientCategoryId);

    List<IngredientWithCategory> readAllIngredients();

    List<IngredientCategory> readAllIngredientCategories();
}
