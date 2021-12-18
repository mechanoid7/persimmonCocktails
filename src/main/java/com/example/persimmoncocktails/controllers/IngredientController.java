package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.ingredient.*;
import com.example.persimmoncocktails.exceptions.TooShortParameterException;
import com.example.persimmoncocktails.models.ingredient.IngredientCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import com.example.persimmoncocktails.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ingredient")
@PreAuthorize("isAuthenticated")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PreAuthorize("hasAuthority('content:update')")
    @GetMapping("/{ingredientId}")
    public IngredientWithCategory getIngredientById(@PathVariable Long ingredientId) {
        return ingredientService.readIngredientId(ingredientId);
    }

    @GetMapping("/active")
    public List<ResponseIngredientDto> getAllActiveIngredient() {
        return ingredientService.readAllActiveIngredients();
    }

    @GetMapping("/active/{ingredientId}")
    public ResponseIngredientDto getActiveIngredientById(@PathVariable Long ingredientId) {
        return ingredientService.readActiveIngredientId(ingredientId);
    }


    @GetMapping
    public List<IngredientWithCategory> getAllIngredients() {
        return ingredientService.readAllIngredients();
    }

    @GetMapping("/categories")
    public List<IngredientCategory> getAllIngredientCategories() {
        return ingredientService.readAllIngredientCategories();
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PostMapping
    public IngredientWithCategory createIngredient(@RequestBody RequestIngredientDto requestIngredientDto) {
        return ingredientService.createIngredient(requestIngredientDto);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/update-name")
    public void updateName(@RequestBody RequestUpdateIngredientNameDto updateIngredientNameDto) {
        ingredientService.updateName(updateIngredientNameDto.getIngredientId(), updateIngredientNameDto.getName());
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/update-photo")
    public void updatePhoto(@RequestBody RequestUpdateIngredientPhotoDto updateIngredientPhotoDto) {
        ingredientService.updatePhoto(updateIngredientPhotoDto.getIngredientId(),
                updateIngredientPhotoDto.getIngredientPhotoId());
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/update-category")
    public void updateCategory(@RequestBody RequestUpdateIngredientCategoryDto updateIngredientCategoryDto) {
        ingredientService.updateIngredientCategory(updateIngredientCategoryDto.getIngredientId(),
                updateIngredientCategoryDto.getIngredientCategoryId());
    }


    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/deactivate/{ingredientId}")
    public void deactivateIngredient(@PathVariable Long ingredientId) {
        ingredientService.deactivate(ingredientId);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/activate/{ingredientId}")
    public void activateIngredient(@PathVariable Long ingredientId) {
        ingredientService.activate(ingredientId);
    }

    @GetMapping("/active/search-by-prefix")
    public List<IngredientNameDto> getIngredientsByPrefixOfName(@RequestParam String prefix) {
        int minLengthLimit = 2;
        if (Strings.isEmpty(prefix) || prefix.length() < minLengthLimit)
            throw new TooShortParameterException("prefix", minLengthLimit);
        return ingredientService.findActiveIngredientsByPrefix(prefix);
    }
}
