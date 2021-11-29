package com.example.persimmoncocktails.dao;


import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;
import com.example.persimmoncocktails.models.Stock.Ingredient;

import java.util.List;

public interface StockDao {

    void add(StockIngredientsDto stockIngredientsDto);

    void delete(Long ingredientId);

    void update(int amount, Long IngredientId);

    List<StockIngredientsDto> getStockIngredients(Long stockId);

}
