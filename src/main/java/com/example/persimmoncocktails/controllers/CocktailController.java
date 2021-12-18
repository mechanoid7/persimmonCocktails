package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.cocktail.*;
import com.example.persimmoncocktails.models.cocktail.CocktailCategory;
import com.example.persimmoncocktails.services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cocktail")
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @PreAuthorize("hasAuthority('inactive:read')")
    @GetMapping("/{dishId}")
    public FullCocktailDto getCocktailById(@PathVariable Long dishId) {
        return cocktailService.readById(dishId, true);
    }

    @GetMapping("/active/{dishId}")
    public FullCocktailDto getRestrictedCocktailInfo(@PathVariable Long dishId) {
        return cocktailService.readById(dishId, false);
    }

    @GetMapping("/{dishId}/likes")
    public Long getLikesById(@PathVariable Long dishId) {
        return cocktailService.getLikes(dishId);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PostMapping("/create")
    public FullCocktailDto createCocktail(@Valid @RequestBody RequestCreateCocktail cocktail) {
        return cocktailService.create(cocktail);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PostMapping("/update-image")
    public void updateImage(@RequestBody RequestChangeImageDto requestChangeImage) {
        cocktailService.updateImage(requestChangeImage);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PostMapping("/delete")
    public void deleteCocktailById(@RequestBody Long dishId) {
        cocktailService.deleteById(dishId);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/update")
    public void updateCocktail(@Valid @RequestBody RequestCocktailUpdate cocktail) {
        cocktailService.update(cocktail);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/change-status")
    public void changeStatus(@RequestBody Long dishId) { // activate/deactivate
        cocktailService.changeStatus(dishId);
    }

    @GetMapping("/{dishId}/is-active")
    public Boolean dishIsActive(@PathVariable Long dishId) {
        return cocktailService.isActive(dishId);
    }

    @PreAuthorize("isAuthenticated")
    @PostMapping("/like")
    public void addLike(@RequestBody Long dishId) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        cocktailService.addLike(dishId, personId);
    }

    @PreAuthorize("hasAuthority('inactive:read')")
    @GetMapping("/search")
    public CocktailsSearchResultDto searchFilterSortCocktails(
            @RequestParam(value = "search", required = false) String searchRequest,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "dish-type", required = false) String dishType,
            @RequestParam(value = "dish-category-id", required = false) Long dishCategoryId,
            @RequestParam(value = "sort-direction", required = false) Boolean sortDirection,
            @RequestParam(value = "ingredients", required = false) List<Long> ingredients,
            @RequestParam(value = "show-active", required = false) Boolean showActive,
            @RequestParam(value = "show-inactive", required = false) Boolean showInactive,
            @RequestParam(value = "calculate-pages-amount", required = true) Boolean calculatePagesAmount,
            @RequestParam("page") Long pageNumber) {
        RequestCocktailSelectDto searchRequestObject =
                new RequestCocktailSelectDto(searchRequest, sortBy, dishType,
                        dishCategoryId, sortDirection, ingredients,
                        showActive == null ? true : showActive,
                        showInactive == null ? true : showInactive);
        CocktailsSearchResultDto result = new CocktailsSearchResultDto(
                cocktailService.searchFilterSort(searchRequestObject, pageNumber),
                null
        );
        if(calculatePagesAmount) result.setAmountOfPages(cocktailService.amountOfResultPages(searchRequestObject));
        return result;
    }

    @GetMapping("/active/search")
    public CocktailsSearchResultDto restrictedSearchFilterSort(
            @RequestParam(value = "search", required = false) String searchRequest,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "dish-type", required = false) String dishType,
            @RequestParam(value = "dish-category-id", required = false) Long dishCategoryId,
            @RequestParam(value = "sort-direction", required = false) Boolean sortDirection,
            @RequestParam(value = "ingredients", required = false) List<Long> ingredients,
            @RequestParam(value = "calculate-pages-amount", required = true) Boolean calculatePagesAmount,
            @RequestParam("page") Long pageNumber) {
        RequestCocktailSelectDto searchRequestObject =
                new RequestCocktailSelectDto(searchRequest, sortBy, dishType, dishCategoryId,
                sortDirection, ingredients, true, false);
        CocktailsSearchResultDto result = new CocktailsSearchResultDto(
                cocktailService.searchFilterSort(searchRequestObject, pageNumber),
                null
        );
        if(calculatePagesAmount) result.setAmountOfPages(cocktailService.amountOfResultPages(searchRequestObject));
        return result;
    }

    @GetMapping("/labels")
    public List<String> getLabelsById(@RequestParam Long dishId) {
        return cocktailService.getLabels(dishId);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PostMapping("/labels/add")
    public void addLabelById(@RequestBody RequestCocktailLabelDto requestCocktailLabelDto) {
        cocktailService.addLabel(requestCocktailLabelDto.getDishId(), requestCocktailLabelDto.getLabel());
    }

    @PreAuthorize("hasAuthority('content:update')")
    @DeleteMapping("/labels/delete")
    public void deleteLabelById(@RequestBody RequestCocktailLabelDto requestCocktailLabelDto) {
        cocktailService.deleteLabel(requestCocktailLabelDto.getDishId(), requestCocktailLabelDto.getLabel());
    }

    @PreAuthorize("hasAuthority('content:update')")
    @DeleteMapping("/labels/clear")
    public void clearLabelsById(@RequestBody Long dishId) {
        cocktailService.clearLabelsLabels(dishId);
    }

    @GetMapping("/categories")
    public List<CocktailCategory> getCocktailCategories() {
        return cocktailService.getCocktailCategories();
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/ingredient/add")
    public void addIngredientToCocktail(@RequestBody RequestIngredientCocktailDto request) {
        cocktailService.addIngredient(request);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/ingredient/remove")
    public void removeIngredientFromCocktail(@RequestBody RequestIngredientCocktailDto request) {
        cocktailService.removeIngredient(request);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/kitchenware/add")
    public void addKitchenwareToCocktail(@RequestBody RequestKitchenwareCocktailDto request) {
        cocktailService.addKitchenware(request);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/kitchenware/remove")
    public void removeKitchenwareFromCocktail(@RequestBody RequestKitchenwareCocktailDto request) {
        cocktailService.removeKitchenware(request);
    }
}

