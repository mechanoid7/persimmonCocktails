package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.models.Cocktail;

import java.util.List;

public interface CocktailDao {
    void create(RequestCreateCocktail cocktail);

    Cocktail readById(Long dishId);

    void update(Cocktail cocktail);

    void deleteById(Long dishId);
    
    void addLikes(Long dishId, Long likeNumber);

    Long getLikes(Long dishId);

    void setLikes(Long dishId, Long likeNumber);

    List<Cocktail> searchFilterSort(String search, List<String> filter, String sort);

    boolean cocktailIsActive(Long dishId);

    void deactivateCocktailById(Long dishId);

    void activateCocktailById(Long dishId);

    Boolean columnExists(String column);
}