package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.auth.RequestChangePasswordDataDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailSelectDto;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Cocktail;
import com.example.persimmoncocktails.services.CocktailService;
import com.example.persimmoncocktails.services.FriendsService;
import com.example.persimmoncocktails.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocktail")
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }


    @GetMapping("/{dishId}")
    public Cocktail getCocktailById(@PathVariable Long dishId) {
        return cocktailService.readById(dishId);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PatchMapping("/update")
    public void updateName(@RequestBody Cocktail cocktail) {
        cocktailService.update(cocktail);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PatchMapping("/change-status")
    public void changeStatus(@RequestBody Long dishId) { // activate/deactivate
        cocktailService.changeStatus(dishId);
    }

    @PatchMapping("/is-active")
    public Boolean dishIsActive(@RequestBody Long dishId) {
        return cocktailService.isActive(dishId);
    }

    @PatchMapping("/like")
    public void addLike(@RequestBody Long dishId) {
        cocktailService.addLike(dishId);
    }


    @GetMapping("/search")
    public List<Cocktail> getPersonsBySubstring(
            @RequestParam(value = "search", required = false) String searchRequest,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "dish-type", required = false) String dishType,
            @RequestParam(value = "dish-category-id", required = false) Long dishCategoryId,
            @RequestParam(value = "sort-direction", required = false) Boolean sortDirection,
            @RequestParam("page") Long pageNumber) {
        return cocktailService.searchFilterSort(new RequestCocktailSelectDto(searchRequest, sortBy, dishType, dishCategoryId, sortDirection), pageNumber);
    }
}

