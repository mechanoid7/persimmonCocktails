package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.StockDao;
import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;
import com.example.persimmoncocktails.models.Stock.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockService {
    StockDao stockDao;

    public void delete(Long ingredientId) {
        stockDao.delete(ingredientId);
    }
    public void add(Long id, String name, String measureType, int amount) {
        StockIngredientsDto stockIngredientsDto = new StockIngredientsDto();
        stockIngredientsDto.setId(id);
        stockIngredientsDto.setName(name);
        stockIngredientsDto.setMeasureType(measureType);
        stockIngredientsDto.setAmount(amount);
        stockDao.add(stockIngredientsDto);
    }
    public void update(int amount, Long ingredientId) {
        stockDao.update(amount, ingredientId);
    }
    public List<StockIngredientsDto> getStockIngredients(Long stockId) {
        return stockDao.getStockIngredients(stockId);
    }
}
