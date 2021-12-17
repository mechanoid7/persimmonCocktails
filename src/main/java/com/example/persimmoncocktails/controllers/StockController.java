package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.stock.*;
import com.example.persimmoncocktails.services.StockService;
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

    @GetMapping("/getPersonalStock")
    public List<StockInfoDto> readStockByPersonId(@RequestParam("page") Long PageNumber) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return stockService.getStockIngredients(personId, PageNumber);
    }

    @GetMapping("/getPersonalStockIngredient/{ingredientId}")
    public StockInfoDto readStockIngredientById(@PathVariable ("ingredientId") Long ingredientId) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return stockService.getStockIngredient(personId, ingredientId);
    }

    @DeleteMapping("/{ingredientId}")
    public void deleteIngredientById(@PathVariable Long ingredientId) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        stockService.delete(ingredientId, personId);
    }

    @PatchMapping("/update-stock")
    public void updateStock(@RequestBody RequestStockUpdateDto requestStockUpdateDto) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        stockService.update(personId, requestStockUpdateDto);
    }

    @PostMapping("/add-ingredient")
    public void addIngredient(@RequestBody RequestAddStockIngredientDto stockIngredients) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        stockService.addIngredient(stockIngredients, personId);
    }

    @PostMapping("/add-ingredientId")
    public void addIngredientId(@RequestBody RequestStockIngredientIdDto stockIngredient) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        stockService.addIngredientId(stockIngredient, personId);
    }

    @GetMapping("/search/{substring}")
    public List<RequestStockSearchIngredientDto> getIngredientBySubstring(@PathVariable String substring, @RequestParam("page") Long pageNumber) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return stockService.searchIngredientByNameSubstring(personId, substring, pageNumber);
    }

    @GetMapping("/search/filter")
    public List<StockInfoDto> searchFilterSortStock(
            @RequestParam(value = "search", required = false) String searchRequest,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "ingredient-category-id", required = false) Long ingredientCategoryId,
            @RequestParam(value = "ingredient-category-name", required = false) String ingredientCategoryName,
            @RequestParam(value = "sort-direction", required = false) Boolean sortDirection,
            @RequestParam("page") Long pageNumber) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return stockService.searchFilterSort(
                new RequestStockIngredientSelectDto(
                        personId, searchRequest, sortBy, ingredientCategoryId, ingredientCategoryName, sortDirection), pageNumber);
    }
}
