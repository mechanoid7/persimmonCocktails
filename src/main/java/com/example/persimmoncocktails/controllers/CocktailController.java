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

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/create")
    public BasicCocktailDto createCocktail(@Valid @RequestBody RequestCreateCocktail cocktail) {
        return cocktailService.create(cocktail);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/update-image")
    public void updateImage(@RequestBody RequestChangeImageDto requestChangeImage) {
        cocktailService.updateImage(requestChangeImage);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/delete")
    public void deleteCocktailById(@RequestBody Long dishId) {
        cocktailService.deleteById(dishId);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PatchMapping("/update")
    public void updateCocktail(@Valid @RequestBody RequestCocktailUpdate cocktail) {
        cocktailService.update(cocktail);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
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
    public List<BasicCocktailDto> searchFilterSortCocktails(
            @RequestParam(value = "search", required = false) String searchRequest,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "dish-type", required = false) String dishType,
            @RequestParam(value = "dish-category-id", required = false) Long dishCategoryId,
            @RequestParam(value = "sort-direction", required = false) Boolean sortDirection,
            @RequestParam(value = "ingredients", required = false) List<Long> ingredients,
            @RequestParam(value = "show-active", required = false) Boolean showActive,
            @RequestParam(value = "show-inactive", required = false) Boolean showInactive,
            @RequestParam("page") Long pageNumber) {
        return cocktailService.searchFilterSort(new RequestCocktailSelectDto(searchRequest, sortBy, dishType,
                dishCategoryId, sortDirection, ingredients, showActive == null ? true : showActive,
                showInactive == null ? true : showInactive), pageNumber);
    }

    @GetMapping("/active/search")
    public List<BasicCocktailDto> restrictedSearchFilterSort(
            @RequestParam(value = "search", required = false) String searchRequest,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "dish-type", required = false) String dishType,
            @RequestParam(value = "dish-category-id", required = false) Long dishCategoryId,
            @RequestParam(value = "sort-direction", required = false) Boolean sortDirection,
            @RequestParam(value = "ingredients", required = false) List<Long> ingredients,
            @RequestParam("page") Long pageNumber) {
        return cocktailService.searchFilterSort(new RequestCocktailSelectDto(searchRequest, sortBy, dishType, dishCategoryId,
                sortDirection, ingredients, true, false), pageNumber);
    }

    @GetMapping("/labels")
    public List<String> getLabelsById(@RequestParam Long dishId) {
        return cocktailService.getLabels(dishId);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/labels/add")
    public void addLabelById(@RequestBody RequestCocktailLabelDto requestCocktailLabelDto) {
        cocktailService.addLabel(requestCocktailLabelDto.getDishId(), requestCocktailLabelDto.getLabel());
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @DeleteMapping("/labels/delete")
    public void deleteLabelById(@RequestBody RequestCocktailLabelDto requestCocktailLabelDto) {
        cocktailService.deleteLabel(requestCocktailLabelDto.getDishId(), requestCocktailLabelDto.getLabel());
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @DeleteMapping("/labels/clear")
    public void clearLabelsById(@RequestBody Long dishId) {
        cocktailService.clearLabelsLabels(dishId);
    }

    @GetMapping("/categories")
    public List<CocktailCategory> getCocktailCategories() {
        return cocktailService.getCocktailCategories();
    }

    @PatchMapping("/ingredient/add")
    public void addIngredientToCocktail(@RequestBody RequestIngredientCocktailDto request) {
        cocktailService.addIngredient(request);
    }

    @PatchMapping("/ingredient/remove")
    public void removeIngredientFromCocktail(@RequestBody RequestIngredientCocktailDto request) {
        cocktailService.removeIngredient(request);
    }

    @PatchMapping("/kitchenware/add")
    public void addKitchenwareToCocktail(@RequestBody RequestKitchenwareCocktailDto request) {
        cocktailService.addKitchenware(request);
    }

    @PatchMapping("/kitchenware/remove")
    public void removeKitchenwareFromCocktail(@RequestBody RequestKitchenwareCocktailDto request) {
        cocktailService.removeKitchenware(request);
    }
}

