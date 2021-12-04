package com.example.persimmoncocktails.dao;


import com.example.persimmoncocktails.dtos.cocktail.BasicCocktailDto;
import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;

import java.util.List;

public interface StockDao {

    void add(StockIngredientsDto stockIngredientsDto);

    void delete(Long ingredientId);

    void update(int amount, Long IngredientId, Long photoId);

    List<StockIngredientsDto> getStockIngredients(Long stockId);

    List<StockIngredientsDto> searchIngredientByNameSubstring(String name, Long pageNumber);

    List<StockIngredientsDto> searchFilterSort(String sqlRequest, Long pageNumber);

}
