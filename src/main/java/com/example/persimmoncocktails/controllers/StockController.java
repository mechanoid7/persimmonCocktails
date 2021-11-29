package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import com.example.persimmoncocktails.services.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{stockId}")
    public List<StockIngredientsDto> readStockById(@PathVariable Long stockId) {
        return stockService.getStockIngredients(stockId);
    }

    @DeleteMapping("/{ingredientId}")
    public void deleteIngredientById(@PathVariable Long ingredientId) {
        stockService.delete(ingredientId);
    }

    @PatchMapping("/update-stock")
    public void updateStock(@RequestParam int amount, @RequestParam Long ingredientId) {
        stockService.update(amount, ingredientId);
    }

    @PostMapping("/add-ingredient")
    public void addIngredient(@RequestBody Long id, @RequestBody String name, @RequestBody String measureType, @RequestBody int amount) {
        stockService.add(id, name, measureType, amount);
    }


}
