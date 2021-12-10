package com.example.persimmoncocktails.dao;


import com.example.persimmoncocktails.dtos.stock.RequestStockSearchIngredientDto;
import com.example.persimmoncocktails.dtos.stock.StockInfoDto;
import com.example.persimmoncocktails.dtos.stock.RequestAddStockIngredientDto;
import com.example.persimmoncocktails.dtos.stock.RequestStockUpdateDto;

import java.util.List;

public interface StockDao {

    void addIngredient(RequestAddStockIngredientDto requestAddStockIngredientDto, Long personId);

    void delete(Long ingredientId, Long personId);

    void update(Long personId, RequestStockUpdateDto requestStockUpdateDto);

    public void updateAmount(int amount, Long ingredientId);

    List<StockInfoDto> getStockIngredients(Long personId);

    List<RequestStockSearchIngredientDto> searchIngredientByNameSubstring(Long personId, String name, Long pageNumber);

    List<RequestAddStockIngredientDto> searchFilterSort(String sqlRequest, Long pageNumber);

    StockInfoDto getStockInfoDto(Long personId);
}
