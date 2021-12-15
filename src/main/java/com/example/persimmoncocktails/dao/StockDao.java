package com.example.persimmoncocktails.dao;


import com.example.persimmoncocktails.dtos.stock.*;

import java.util.List;

public interface StockDao {

    void addIngredient(RequestAddStockIngredientDto requestAddStockIngredientDto, Long personId);

    void delete(Long ingredientId, Long personId);

    void update(Long personId, RequestStockUpdateDto requestStockUpdateDto);

    StockInfoDto getStockIngredient(Long personId, Long ingredientId);

    List<StockInfoDto> getStockIngredients(Long personId, Long ingredientsPerPage);

    List<RequestStockSearchIngredientDto> searchIngredientByNameSubstring(Long personId, String name, Long pageNumber);

    List<StockInfoDto> searchFilterSort(String sqlRequest, Long pageNumber);

    StockInfoDto getStockInfoDto(Long personId);
}
