package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.cocktail.BasicCocktailDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailSelectDto;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.stock.RequestStockIngredientSelectDto;
import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;
import com.example.persimmoncocktails.models.Stock.StockIngredient;
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
    public void updateStock(@RequestParam int amount, @RequestParam Long ingredientId, @RequestParam Long photoId) {
        stockService.update(amount, ingredientId, photoId);
    }

    @PostMapping("/add-ingredient")
    public void addIngredient(@RequestBody Long id, @RequestBody String name, @RequestBody String measureType, @RequestBody int amount) {
        stockService.add(id, name, measureType, amount);
    }

    @GetMapping("/search/{substring}")
    public List<StockIngredientsDto> getPersonsBySubstring(@PathVariable String substring, @RequestParam("page") Long pageNumber) {
        return stockService.searchIngredientByNameSubstring(substring, pageNumber);
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/search")
    public List<StockIngredientsDto> searchFilterSortStock(
            @RequestParam(value = "search", required = false) String searchRequest,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "amount", required = false) int amount,
            @RequestParam(value = "measure-type", required = false) String measureType,
            @RequestParam(value = "ingredient-category-id", required = false) Long ingredientCategoryId,
            @RequestParam(value = "sort-direction", required = false) Boolean sortDirection,
            @RequestParam("page") Long pageNumber) {
        return stockService.searchFilterSort(new RequestStockIngredientSelectDto(searchRequest, sortBy, amount, measureType, ingredientCategoryId, sortDirection), pageNumber);
    }

}
